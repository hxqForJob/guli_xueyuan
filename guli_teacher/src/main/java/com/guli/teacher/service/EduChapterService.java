package com.guli.teacher.service;

import com.guli.teacher.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hxq
 * @since 2020-03-22
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程id获取章节和小节
     * @param courseId
     * @return
     */
    List<ChapterVo> getChapterAndVideoByCourseId(String courseId);

    /**
     * 保存或更新章节
     * @param chapter
     * @return
     */
    boolean cusSaveOrUpdate(EduChapter chapter);

    /**
     * 删除章节
     * @param id
     * @return
     */
    boolean cusDeleteChapter(String id);
}
