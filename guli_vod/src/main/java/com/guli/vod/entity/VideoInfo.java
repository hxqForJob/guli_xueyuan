package com.guli.vod.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VideoInfo {

    /**
     * 播放凭证
     */
    private String playAuth ;


    /**
     * VideoMeta信息
     */
    private  String videoMetaTitle;

    private  String requestId;
    /**
     * 阿里云上的视频Id
     */
    private  String videoId;

    private  String errorMessage;
}
