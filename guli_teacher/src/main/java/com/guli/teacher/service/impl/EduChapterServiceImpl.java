package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.result.ResultCode;
import com.guli.teacher.entity.EduChapter;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.entity.vo.ChapterVo;
import com.guli.teacher.entity.vo.VideoVo;
import com.guli.teacher.exception.TeacherException;
import com.guli.teacher.mapper.EduChapterMapper;
import com.guli.teacher.mapper.EduVideoMapper;
import com.guli.teacher.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoMapper eduVideoMapper;

    /**
     *根据课程id获取章节和小节
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterAndVideoByCourseId(String courseId) {
        List<ChapterVo> chapterVos=new ArrayList<>();
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<EduChapter>()
                .eq("course_id", courseId)
                .orderByAsc("sort");
        //根据课程Id查询eduChapters
        List<EduChapter> eduChapters = baseMapper.selectList(chapterQueryWrapper);
        if(eduChapters!=null&&eduChapters.size()>0)
        {
            //遍历该课程的所有章节
            for ( EduChapter eduChapter:eduChapters) {
                ChapterVo chapterVo=new ChapterVo();
                //数据copy
                BeanUtils.copyProperties(eduChapter,chapterVo);
                //根据章节Id查询所有的小节
                QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<EduVideo>()
                        .eq("course_id", courseId)
                        .eq("chapter_id",chapterVo.getId())
                        .orderByAsc("sort");
                List<EduVideo> eduVideos = eduVideoMapper.selectList(videoQueryWrapper);
                if(eduVideos!=null&&eduVideos.size()>0)
                {
                    for (EduVideo video:eduVideos){
                        VideoVo videoVo=new VideoVo();
                        //数据copy
                        BeanUtils.copyProperties(video,videoVo);
                        chapterVo.getChildren().add(videoVo);
                    }
                }
                chapterVos.add(chapterVo);
            }
        }
        return  chapterVos;
    }

    /**
     *保存或更新章节
     * @param chapter
     * @return
     */
    @Override
    public boolean cusSaveOrUpdate(EduChapter chapter) {



            if(StringUtils.isEmpty(chapter.getId()))
            {
                //判断更新或添加的章节是否存在
                QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<EduChapter>().eq("title", chapter.getTitle()).eq("course_id",chapter.getCourseId());
                List<EduChapter> eduChapters = baseMapper.selectList(chapterQueryWrapper);
                if(eduChapters.size()>0)
                {
                    throw  new TeacherException(ResultCode.ERROR.getCode(),"改章节已存在，请重新填写");

                }
                //添加
                baseMapper.insert(chapter);
            }else
            {
                //判断更新或添加的章节是否存在
                QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<EduChapter>().eq("title", chapter.getTitle()).eq("sort",chapter.getSort()).eq("course_id",chapter.getCourseId());
                List<EduChapter> eduChapters = baseMapper.selectList(chapterQueryWrapper);
                if(eduChapters.size()>0)
                {
                    throw  new TeacherException(ResultCode.ERROR.getCode(),"改章节已存在，请重新填写");

                }
                //更新
                baseMapper.updateById(chapter);
            }
            return  true;

    }

    /**
     *删除章节
     * @param id
     * @return
     */
    @Override
    public boolean cusDeleteChapter(String id) {
        try {
            baseMapper.deleteById(id);
            QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<EduVideo>().eq("chapter_id", id);
            eduVideoMapper.delete(queryWrapper);
            return  true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ExceptionUtil.getMessage(e));
            return  false;
        }
    }
}
