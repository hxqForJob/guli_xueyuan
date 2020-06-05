package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.edu.entity.EduChapter;
import com.guli.edu.entity.EduVideo;
import com.guli.edu.entity.vo.ChapterDto;
import com.guli.edu.entity.vo.VideoDto;
import com.guli.edu.mapper.EduChapterMapper;
import com.guli.edu.mapper.EduVideoMapper;
import com.guli.edu.service.EduChapterService;
import com.guli.result.ResultCode;
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
    public List<ChapterDto> getChapterAndVideoByCourseId(String courseId) {
        List<ChapterDto> chapterVos=new ArrayList<>();
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<EduChapter>()
                .eq("course_id", courseId)
                .orderByAsc("sort");
        //根据课程Id查询eduChapters
        List<EduChapter> eduChapters = baseMapper.selectList(chapterQueryWrapper);
        if(eduChapters!=null&&eduChapters.size()>0)
        {
            //遍历该课程的所有章节
            for ( EduChapter eduChapter:eduChapters) {
                ChapterDto chapterVo=new ChapterDto();
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
                        VideoDto videoVo=new VideoDto();
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


}
