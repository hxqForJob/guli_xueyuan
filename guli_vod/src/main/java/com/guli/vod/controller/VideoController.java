package com.guli.vod.controller;

import com.guli.result.Result;
import com.guli.vod.entity.VideoInfo;
import com.guli.vod.service.VoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(description = "视频控制器")
@RestController
@RequestMapping("uploadVideo")
@CrossOrigin
public class VideoController {
    @Autowired
    private VoService voService;

    @PostMapping("/upload")
    @ApiOperation("上传视频")
    public Result uploadVideo(MultipartFile file,String title)
    {

       String videoId=  voService.uploadFile(file,title);
       if(StringUtils.isEmpty(videoId))
       {
           return  Result.error().message("上传失败");
       }
       return  Result.ok().data("videoId",videoId).data("videoFileName",file.getOriginalFilename());
    }

    @DeleteMapping("/deleteVideo/{videoId}")
    @ApiOperation("删除视频")
    public Result deleteVideo(@PathVariable("videoId") String videoId)
    {

        boolean flg=  voService.deleteVideo(videoId);
        if(!flg)
        {
            return  Result.error().message("删除失败");
        }
        return  Result.ok();
    }


    @DeleteMapping("/deleteVideos")
    @ApiOperation("删除多个视频")
    public Result deleteVideos(@RequestParam("videoIdList") List<String> videoIds)
    {

        boolean flg=  voService.deleteVideo(videoIds);
        if(!flg)
        {
            return  Result.error().message("删除失败");
        }
        return  Result.ok();
    }
    @GetMapping("/getAuth/{videoId}")
    @ApiOperation("获取播放链接和凭证")
    public Result getAuth(@PathVariable("videoId") String videoId)
    {

        VideoInfo videoInfo =  voService.getAuth(videoId);
        if(videoInfo==null)
        {
            return  Result.error().message("获取失败");
        }
        return  Result.ok().data("videoInfo",videoInfo);
    }
}
