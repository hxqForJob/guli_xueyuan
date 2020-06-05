package com.guli.oss.controller;

import com.guli.oss.service.FileService;
import com.guli.oss.util.ConstantPropertiesUtil;
import com.guli.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "文件上传")
@RestController
@RequestMapping("/uploadOss")
@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/uploadAvatar")
    @ApiOperation("上传头像")
    public Result uploadAvatar(
           @ApiParam(name = "file",value = "图片",required = true)
            MultipartFile file)
    {
        String url = fileService.uploadFileToOss(file);
        return  Result.ok().data("url",url);
    }

    @PostMapping("/upload")
    @ApiOperation("上传课程封面")
    public Result upload(
            @ApiParam(name = "file",value = "图片",required = true)
                    MultipartFile file,String host)
    {
        if(!StringUtils.isEmpty(host))
        {
            ConstantPropertiesUtil.FILE_HOST=host;
        }
        String url = fileService.uploadFileToOss(file);
        return  Result.ok().data("url",url);
    }
}
