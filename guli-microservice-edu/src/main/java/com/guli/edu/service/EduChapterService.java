package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.EduChapter;
import com.guli.edu.entity.vo.ChapterDto;

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
    List<ChapterDto> getChapterAndVideoByCourseId(String courseId);

}
