package com.guli.edu.entity.vo;

import lombok.Data;

//小节
@Data
public class VideoDto {
    private String id;
    private String title;
    private Boolean isFree;
    private String videoSourceId;  //视频id

}
