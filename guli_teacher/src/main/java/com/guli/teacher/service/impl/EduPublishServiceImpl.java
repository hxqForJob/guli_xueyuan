package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.result.ResultCode;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.exception.TeacherException;
import com.guli.teacher.mapper.EduCourseMapper;
import com.guli.teacher.mapper.EduPublishMapper;
import com.guli.teacher.mapper.EduVideoMapper;
import com.guli.teacher.service.EduPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 发布服务实现
 */
@Service
public class EduPublishServiceImpl implements EduPublishService {

    @Autowired
    private EduCourseMapper eduCourseMapper;
    @Autowired
    private EduVideoMapper eduVideoMapper;
    @Autowired
    private EduPublishMapper eduPublishMapper;
    //获取发布信息
    @Override
    public Map<String, Object> getPublishInfo(String id) {
       Map<String, Object> publishInfos = eduPublishMapper.getPublishInfo(id);
        return  publishInfos;
    }

    //发布课程
    @Override
    public boolean publishCourse(String id) {
        try {
            EduCourse eduCourse = eduCourseMapper.selectById(id);
            eduCourse.setStatus("Normal");
            eduCourseMapper.updateById(eduCourse);
            QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<EduVideo>().eq("course_id", id);
            List<EduVideo> eduVideos = eduVideoMapper.selectList(videoQueryWrapper);
            for (EduVideo ed:eduVideos) {
                ed.setStatus("Normal");
                eduVideoMapper.updateById(ed);
            }
            return  true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new TeacherException(ResultCode.ERROR.getCode(),"发布失败，请查看日志",e.getMessage());
        }

    }

}
