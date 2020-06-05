package com.guli.teacher.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EduPublishMapper {

    Map<String,Object> getPublishInfo(String id);
}
