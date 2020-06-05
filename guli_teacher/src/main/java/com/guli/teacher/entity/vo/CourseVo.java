package com.guli.teacher.entity.vo;

import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.EduCourseDescription;
import com.guli.teacher.mapper.EduChapterMapper;
import lombok.Data;

import java.io.Serializable;

@Data
public class CourseVo implements Serializable {
    private EduCourse eduCourse;
    private EduCourseDescription eduCourseDescription;
}
