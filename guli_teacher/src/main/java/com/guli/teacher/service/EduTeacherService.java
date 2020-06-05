package com.guli.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.query.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author hxq
 * @since 2020-03-16
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 分页查询讲师
     * @param pageParam page参数
     * @param teacherQuery 条件
     */
    void getEduTeacherByPage(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

}
