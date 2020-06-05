package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.vo.CourseWebVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hxq
 * @since 2020-03-31
 */
public interface CourseService extends IService<Course> {

    /**
     * 根据讲师Id查询讲师所讲课程
     * @param teacherId
     * @return
     */
    List<Course> selectByTeacherId(String teacherId);

    /**
     * 分页查询课程
     * @param pageParam
     * @return
     */
    Map<String, Object> pageListWeb(Page<Course> pageParam);

    //根据id查询课程基本信息
    CourseWebVo getCourseInfoFrontId(String id);
}
