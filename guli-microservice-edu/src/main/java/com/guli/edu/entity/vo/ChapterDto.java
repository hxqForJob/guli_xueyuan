package com.guli.edu.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//章节
@Data
public class ChapterDto {
    private String id;
    private String title;

    //表示所有的小节
    private List<VideoDto> children = new ArrayList<>();


}
