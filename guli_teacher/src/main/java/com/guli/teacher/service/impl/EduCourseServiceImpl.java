package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.result.Result;
import com.guli.result.ResultCode;
import com.guli.teacher.client.VoClient;
import com.guli.teacher.entity.EduChapter;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.EduCourseDescription;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.entity.query.CourseQuery;
import com.guli.teacher.entity.vo.CourseVo;
import com.guli.teacher.mapper.EduChapterMapper;
import com.guli.teacher.mapper.EduCourseDescriptionMapper;
import com.guli.teacher.mapper.EduCourseMapper;
import com.guli.teacher.mapper.EduVideoMapper;
import com.guli.teacher.service.EduCourseDescriptionService;
import com.guli.teacher.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author hxq
 * @since 2020-03-22
 */
@Service
@Slf4j
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    /**
     * 课程简介数据访问
     */
    @Autowired
    private EduCourseDescriptionMapper eduCourseDescriptionMapper;

    /**
     * 章节数据访问
     */
    @Autowired
    private EduChapterMapper eduChapterMapper;

    /**
     * 视频访问
     */
    @Autowired
    private EduVideoMapper eduVideoMapper;

    /**
     * 云端视频微服务
     */
    @Autowired
    private VoClient voClient;

    /**
     * 添加课程基本信息
     * @param courseVo 课程信息和课程附件信息包装类
     * @return
     */
    @Transactional
    public String addCourseInfo(CourseVo courseVo) {
        try {
            if(StringUtils.isEmpty(courseVo.getEduCourse().getId()))
            {
                //添加
                baseMapper.insert(courseVo.getEduCourse());
                String courseId=courseVo.getEduCourse().getId();
                courseVo.getEduCourse().setStatus("Draft");
                courseVo.getEduCourseDescription().setId(courseId);
                eduCourseDescriptionMapper.insert(courseVo.getEduCourseDescription());
                return  courseId;
            }else
            {
                //修改
                EduCourse newEduCourse=courseVo.getEduCourse();
                EduCourse eduCourse=baseMapper.selectById(courseVo.getEduCourse().getId());
                BeanUtils.copyProperties(newEduCourse,eduCourse);
                baseMapper.updateById(eduCourse);
                EduCourseDescription newCourseDescription = courseVo.getEduCourseDescription();
                EduCourseDescription eduCourseDescription = eduCourseDescriptionMapper.selectById(newEduCourse.getId());
                if(eduCourseDescription==null)
                {
                    newCourseDescription.setId(newEduCourse.getId());
                    eduCourseDescriptionMapper.insert(newCourseDescription);
                }else {
                    eduCourseDescription.setDescription(newCourseDescription.getDescription());
                    eduCourseDescriptionMapper.updateById(eduCourseDescription);
                }

                return  eduCourseDescription.getId();
            }


        }catch (Exception e)
        {
            e.printStackTrace();
            //写入日志
            log.error(ExceptionUtil.getMessage(e));
            return  null;
        }

    }

    /**
     * 根据id查询课程基本信息
     * @param id
     * @return
     */
    @Override
    public CourseVo getCoureseVo(String id) {
        CourseVo courseVo=new CourseVo();
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        EduCourse eduCourse = baseMapper.selectOne(queryWrapper);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionMapper.selectById(id);
        courseVo.setEduCourse(eduCourse);
        courseVo.setEduCourseDescription(eduCourseDescription);
        return  courseVo;
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("subject_id", subjectId);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Transactional
    @Override
    public boolean deleteCourse(String id) {

        try {
            //删除课程
            baseMapper.deleteById(id);
            //删除课程描述
            eduCourseDescriptionMapper.deleteById(id);
            //删除章节
            QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<EduChapter>().eq("course_id", id);
            eduChapterMapper.delete(chapterWrapper);

            //获取该课程下的所有videoSourceId
            QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<EduVideo>().eq("course_id", id);
            List<EduVideo> eduVideos = eduVideoMapper.selectList(videoWrapper);
            List<String> videoIds=new ArrayList<>();
            for (EduVideo eduVideo:eduVideos ) {
                videoIds.add(eduVideo.getVideoSourceId());
            }
            //删除云端视频
            Result result = voClient.removeVideos(videoIds);
            if(ResultCode.OK.getCode()!=result.getCode())
            {
                return  false;
            }
            //删除小节

            eduVideoMapper.delete(videoWrapper);

            return  true;
        } catch (Exception e) {
            e.printStackTrace();
            //写入日志
            log.error(ExceptionUtil.getMessage(e));
            return  false;
        }
    }

}
