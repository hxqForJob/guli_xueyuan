package com.guli.teacher.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.result.Result;
import com.guli.result.ResultCode;
import com.guli.teacher.entity.EduTeacher;
import com.guli.teacher.entity.query.TeacherQuery;
import com.guli.teacher.exception.TeacherException;
import com.guli.teacher.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author hxq
 * @since 2020-03-16
 */
@RestController
@RequestMapping("teacher")
@Api(description = "讲师管理模块")
@CrossOrigin
public class EduTeacherController {

    /**
     * 教师管理业务逻辑
     */
    @Autowired
    private EduTeacherService eduTeacherService;


    @GetMapping("/getAll")
    @ApiOperation("查询所有")
    public Result getAll()
    {
        try {
            List<EduTeacher> eduTeachers = eduTeacherService.list(null);
            Result result = Result.ok().data("items", eduTeachers);
            return  result;
        }catch (Exception e){
            throw new TeacherException(ResultCode.ERROR.getCode(),"发现异常请查看日志",e.getMessage());
        }
    }

    @ApiOperation("条件分页查询教师")
    @PostMapping("/getPage/{page}/{size}")
    public Result getPage(
            @ApiParam(name = "page",value = "页码",required = true)
            @PathVariable("page")
                    Integer page,
            @ApiParam(name = "size",value = "页大小",required = true)
            @PathVariable("size")
                    Integer size,
            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
            @RequestBody
                    TeacherQuery teacherQuery)
    {
        if(page==null||size==null||page<=0||size<=0)
        {
            throw  new TeacherException(ResultCode.ERROR.getCode(),"页数和页大小不能为空,且不能小于0");
        }
        try {
            Page<EduTeacher> pages=new Page<>(page,size);
            eduTeacherService.getEduTeacherByPage(pages,teacherQuery);
            return Result.ok().data("total",pages.getTotal()).data("rows",pages.getRecords());
        }catch (Exception e)
        {
            throw  new TeacherException(ResultCode.ERROR.getCode(),"发生异常，请查看日志",e.toString());
        }
    }


    @GetMapping("/getTeacherById/{id}")
    @ApiOperation("根据Id查询")
    public Result getTeacherById(
            @ApiParam(name = "id",value = "id",required = true)
            @PathVariable("id") String id)
    {
        if(StringUtils.isEmpty(id))
        {
            throw new TeacherException(ResultCode.ERROR.getCode(),"id不能为空");
        }
        try {
            EduTeacher eduTeacher = eduTeacherService.getById(id);
            if(eduTeacher==null)
            {
                throw new TeacherException(ResultCode.ERROR.getCode(),"没有当前Id的教师");
            }
            Result result = Result.ok().data("teacher",eduTeacher);
            return  result;
        }catch (Exception e){
            throw new TeacherException(ResultCode.ERROR.getCode(),"发现异常请查看日志",e.getMessage());
        }
    }


    @PutMapping("/update")
    @ApiOperation("更新教师")
    public Result updateTeacher(
            @ApiParam(name = "eduTeacher",value = "教师信息",required = false)
            @RequestBody EduTeacher eduTeacher)
    {
        try {
           eduTeacherService.updateById(eduTeacher);
            Result result = Result.ok();
            return  result;
        }catch (Exception e){
            throw new TeacherException(ResultCode.ERROR.getCode(),"发现异常请查看日志",e.getMessage());
        }
    }

    @PostMapping("/add")
    @ApiOperation("添加教师")
    public Result addTeacher(
            @ApiParam(name = "eduTeacher",value = "教师信息",required = false)
            @RequestBody EduTeacher eduTeacher)
    {
        try {
            eduTeacherService.save(eduTeacher);
            Result result = Result.ok();
            return  result;
        }catch (Exception e){
            throw new TeacherException(ResultCode.ERROR.getCode(),"发现异常请查看日志",e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除教师")
    public Result deleteTeacher(
            @ApiParam(name = "id",value = "教师id",required = true)
            @PathVariable("id") String id)
    {
        try {
            eduTeacherService.removeById(id);
            Result result = Result.ok();
            return  result;
        }catch (Exception e){
            throw new TeacherException(ResultCode.ERROR.getCode(),"发现异常请查看日志",e.getMessage());
        }
    }



}

