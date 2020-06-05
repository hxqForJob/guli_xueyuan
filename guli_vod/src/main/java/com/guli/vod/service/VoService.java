package com.guli.vod.service;

import com.guli.vod.entity.VideoInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VoService {
    /**
     * 上传视频
     * @param file
     * @return
     */
    String uploadFile(MultipartFile file,String title);

    /**
     * 删除视频
     * @param videoId
     * @return
     */
    boolean deleteVideo(String videoId);

    /**
     * 删除多个视频
     * @param videoIds
     * @return
     */
    boolean deleteVideo(List<String> videoIds);

    /**
     * 获取视频播放凭证和播放链接
     * @param videoId
     * @return
     */
    VideoInfo getAuth(String videoId);
}
