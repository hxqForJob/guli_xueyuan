package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author hxq
 * @since 2020-03-31
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 分页查询教师
     * @param pageTeacher
     * @return
     */
    Map<String, Object> pageListWeb(Page<EduTeacher> pageTeacher);
}
