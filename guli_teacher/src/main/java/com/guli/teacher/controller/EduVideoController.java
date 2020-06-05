package com.guli.teacher.controller;


import com.guli.result.Result;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author hxq
 * @since 2020-03-23
 */
@RestController
@Api(description = "课时管理")
@RequestMapping("/video")
@CrossOrigin
public class EduVideoController {

    /**
     * video业务逻辑
     */
    @Autowired
    private EduVideoService eduVideoService;

    @ApiOperation("根据Id查询课时video")
    @GetMapping("/getVideo/{id}")
    public Result getVideoById(@PathVariable("id") String id)
    {
      EduVideo video= eduVideoService.getById(id);
      if(video!=null)
      {
          return  Result.ok().data("video",video);
      }else
      {
          return  Result.ok().data("video",null);
      }
    }

    @ApiOperation("删除video")
    @DeleteMapping("/deleteVideo/{id}")
    public Result deleteVideo(@PathVariable("id") String id)
    {
        boolean flag= eduVideoService.deleteVideo(id);
        if(flag)
        {
            return  Result.ok();
        }else
        {
            return  Result.error();
        }
    }

    @ApiOperation("添加更新video")
    @PostMapping("/addOrUpdateVideo")
    public Result addOrUpdateVideo(@RequestBody EduVideo eduVideo)
    {
        boolean flag= eduVideoService.addOrUpdateVideo(eduVideo);
        if(flag)
        {
            return  Result.ok();
        }else
        {
            return  Result.error();
        }
    }
}

