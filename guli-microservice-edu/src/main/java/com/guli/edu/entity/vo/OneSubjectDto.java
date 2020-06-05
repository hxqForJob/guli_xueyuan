package com.guli.edu.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//一级分类
@Data
public class OneSubjectDto {

    private String id;//一级分类id
    private String title;//一级分类名称

    //一级分类里面有很多的二级分类
    private List<TwoSubjectDto> children = new ArrayList<>();

}
