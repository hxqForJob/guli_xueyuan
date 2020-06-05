package com.guli.teacher.service;

import java.util.Map;

/**
 * 发布业务服务
 */
public interface EduPublishService {
    //获取发布信息
    Map<String, Object> getPublishInfo(String id);

    //发布课程
    boolean publishCourse(String id);
}
