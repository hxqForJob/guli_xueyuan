package com.guli.edu.mapper;

import com.guli.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.edu.entity.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author hxq
 * @since 2020-03-31
 */
public interface CourseMapper extends BaseMapper<Course> {
    //根据课程id查询课程基本信息
    CourseWebVo getFrontCourseInfoId(String id);
}
