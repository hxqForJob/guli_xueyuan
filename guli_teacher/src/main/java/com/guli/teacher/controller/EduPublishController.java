package com.guli.teacher.controller;


import com.guli.result.Result;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.service.EduPublishService;
import com.guli.teacher.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 发布课程 前端控制器
 * </p>
 *
 * @author hxq
 * @since 2020-03-23
 */
@RestController
@Api(description = "发布课程")
@RequestMapping("/publish")
@CrossOrigin
public class EduPublishController {

    /**
     * video业务逻辑
     */
    @Autowired
    private EduPublishService eduPublishService;

    @ApiOperation("根据课程Id查询发布课程基本信息")
    @GetMapping("/getPublishInfo/{id}")
    public Result getVideoById(@PathVariable("id") String id)
    {
      Map<String,Object> info= eduPublishService.getPublishInfo(id);
      if(info!=null)
      {
          return  Result.ok().data("info",info);
      }else
      {
          return  Result.ok().data("info",null);
      }
    }

    @ApiOperation("发布课程")
    @PutMapping("/publishCourse/{id}")
    public Result addOrUpdateVideo(@PathVariable("id") String id)
    {
        boolean flag= eduPublishService.publishCourse(id);
        if(flag)
        {
            return  Result.ok();
        }else
        {
            return  Result.error();
        }
    }

}

