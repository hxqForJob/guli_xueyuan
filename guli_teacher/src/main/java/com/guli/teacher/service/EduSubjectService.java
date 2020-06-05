package com.guli.teacher.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.EduSubject;
import com.guli.teacher.entity.vo.SubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author hxq
 * @since 2020-03-20
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 导入课程
     * @param file
     * @return
     */
    List<String> importSubject(MultipartFile file) ;

    /**
     * 根据title获取课程
     * @param title
     * @return
     */
    EduSubject getSubByTitle(String title);

    /**
     * 根据title和父级Id获取课程
     * @param title
     * @return
     */
    EduSubject getSubByTitleAndPid(String title,String Pid);

    /**
     * 获取课程分类
     * @return
     */
    List<SubjectVo> getSubjects();

    /**
     * 根据id查询子分类
     * @param id
     * @return
     */
    List<String> getChildrenSubject(String id);

    /**
     * 删除课程分类
     * @param id
     * @return
     */
    boolean deleteSubject(String id);

    /**
     * 添加课程
     * @param subject
     * @return
     */
     boolean addSubject(EduSubject subject);
}
