package com.guli.teacher.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectVo implements Serializable {
    private String id;

    private String title;

    private List<SubjectVo2> children = new ArrayList<SubjectVo2>();

}
