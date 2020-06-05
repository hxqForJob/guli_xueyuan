package com.guli.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.query.CourseQuery;
import com.guli.teacher.entity.vo.CourseVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hxq
 * @since 2020-03-22
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     * @param courseVo 课程信息和课程附件信息包装类
     * @return
     */
    String addCourseInfo(CourseVo courseVo);

    /**
     * 获取课程基本信息
     * @param id
     * @return
     */
    CourseVo getCoureseVo(String id);


    /**
     * 根据条件分页查询课程列表
     * @param pageParam
     * @param courseQuery
     */
    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);


    /**
     * 删除课程
     * @param id
     * @return
     */
    boolean deleteCourse(String id);
}
