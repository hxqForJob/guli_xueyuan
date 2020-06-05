package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.result.Result;
import com.guli.result.ResultCode;
import com.guli.teacher.client.VoClient;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.exception.TeacherException;
import com.guli.teacher.mapper.EduVideoMapper;
import com.guli.teacher.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author hxq
 * @since 2020-03-23
 */
@Service
@Slf4j
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    /**
     * 视频微服务
     */
    @Autowired
    private VoClient voClient;


    /**
     * 添加或更新Video
     * @param eduVideo
     * @return
     */
    @Override
    public boolean addOrUpdateVideo(EduVideo eduVideo) {
        if(StringUtils.isEmpty(eduVideo.getId()))
        {
            //添加
            //判断是否已经添加
            QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<EduVideo>()
                    .eq("course_id", eduVideo.getCourseId())
                    .eq("chapter_id", eduVideo.getChapterId())
                    .eq("title", eduVideo.getTitle());
            List<EduVideo> videos = baseMapper.selectList(videoQueryWrapper);
            if(videos.size()>0){
                throw  new TeacherException(ResultCode.ERROR.getCode(),"改小节已存在，请重新填写");
            }
            try {
                eduVideo.setStatus("Draft");
                baseMapper.insert(eduVideo);
                return  true;
            } catch (Exception e) {
                e.printStackTrace();
                log.error(ExceptionUtil.getMessage(e));
                return  false;
            }
        }else {
            //修改
            QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<EduVideo>()
                    .eq("course_id", eduVideo.getCourseId())
                    .eq("chapter_id", eduVideo.getChapterId())
                    .eq("title", eduVideo.getTitle())
                    .eq("sort",eduVideo.getSort())
                    .eq("is_free",eduVideo.getIsFree())
                    .eq("video_source_id",eduVideo.getVideoSourceId())
                    .eq("duration",eduVideo.getDuration())
                    .eq("size",eduVideo.getSize())
                    .eq("video_file_name",eduVideo.getVideoFileName());
            List<EduVideo> videos = baseMapper.selectList(videoQueryWrapper);
            if(videos.size()>0)
            {
                throw  new TeacherException(ResultCode.ERROR.getCode(),"改小节已存在，请重新填写");
            }
            try {
                baseMapper.updateById(eduVideo);
                return  true;
            } catch (Exception e) {
                e.printStackTrace();
                log.error(ExceptionUtil.getMessage(e));
                return  false;
            }
        }
    }

    /**
     * 删除小节
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean deleteVideo(String id) {
       //TODD 删除云视频
        try {
            EduVideo eduVideo = this.getById(id);
            String videoId=eduVideo.getVideoSourceId();
            Result result = voClient.removeVideo(videoId);
            if(ResultCode.OK.getCode()==result.getCode())
            {
                //云端视频删除成功
                this.removeById(id);
                return  true;
            }
            else {
                return  false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ExceptionUtil.getMessage(e));
            return  false;
        }
    }
}
