package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.EduVideo;


/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author hxq
 * @since 2020-03-23
 */
public interface EduVideoService extends IService<EduVideo> {



    //添加或更新Video
    boolean addOrUpdateVideo(EduVideo eduVideo);

    /**
     * 删除小节
     * @param id
     * @return
     */
    boolean deleteVideo(String id);
}
