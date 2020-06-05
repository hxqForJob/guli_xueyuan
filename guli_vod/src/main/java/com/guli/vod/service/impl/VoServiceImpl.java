package com.guli.vod.service.impl;

import com.guli.util.ExceptionUtil;
import com.guli.vod.entity.VideoInfo;
import com.guli.vod.service.VoService;
import com.guli.vod.util.VoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class VoServiceImpl implements VoService {
    /**
     * 上传视频
     * @param file
     * @return
     */
    @Override
    public String uploadFile(MultipartFile file,String title) {
       String videoId=null;
        try {
            InputStream inputStream=file.getInputStream();
            videoId = VoUtil.UploadStream(title,file.getOriginalFilename(), inputStream);
            return  videoId;
        } catch (IOException e) {
            e.printStackTrace();
            log.error(ExceptionUtil.getMessage(e));
            return  null;
        }
    }

    /**
     * 删除视频
     * @param videoId
     * @return
     */
    @Override
    public boolean deleteVideo(String videoId) {
        boolean flg=false;
        try {
            VoUtil.deleteVideo(videoId);
            flg=true;
        }
        catch (Exception e) {
            //e.printStackTrace();
            log.error(ExceptionUtil.getMessage(e));
           if(e.getMessage().contains("The video does not exist"))
           {
               flg = true;
           }else
           {
               flg=false;
           }
        }
        return flg;
    }

    /**
     * 删除多个视频
     * @param videoIds
     * @return
     */
    @Override
    public boolean deleteVideo(List<String> videoIds) {
        boolean flg=false;
        try {
            VoUtil.deleteVideo(videoIds);
            flg=true;
        }
        catch (Exception e) {
            //e.printStackTrace();
            log.error(ExceptionUtil.getMessage(e));
            if(e.getMessage().contains("The video does not exist"))
            {
                flg = true;
            }else
            {
                flg=false;
            }
        }
        return flg;
    }

    /**
     * 获取播放凭证和播放链接
     * @param videoId
     * @return
     */
    @Override
    public VideoInfo getAuth(String videoId) {
        VideoInfo videoInfo=null;
        try {
            videoInfo = VoUtil.getUploadAuth(videoId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ExceptionUtil.getMessage(e));
        }
        return  videoInfo;
    }
}
