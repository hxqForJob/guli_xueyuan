package com.guli.teacher.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.result.Result;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.query.CourseQuery;
import com.guli.teacher.entity.vo.CourseVo;
import com.guli.teacher.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author hxq
 * @since 2020-03-22
 */
@RestController
@Api(description = "课程管理")
@RequestMapping("/course")
@CrossOrigin
public class EduCourseController {

    /**
     * 课程业务逻辑
     */
    @Autowired
    private EduCourseService courseService;

    @PostMapping("addCourseInfo")
    @ApiOperation("添加课程基信息")
    public Result addCourseInfo(@RequestBody CourseVo courseVo)
    {
       String couresId= courseService.addCourseInfo(courseVo);
       if(!StringUtils.isEmpty(couresId))
       {
           return Result.ok().data("id",couresId);
       }else {
           return Result.error();
       }
    }

    @GetMapping("getCourseById/{id}")
    @ApiOperation("根据课程Id查询课程基本信息")
    public  Result getCourseById(@PathVariable("id") String id)
    {
        CourseVo eduCourse=courseService.getCoureseVo(id);
        if(eduCourse!=null)
        {
            return  Result.ok().data("course",eduCourse);
        }
        return  Result.ok().data("course",null);
    }

    @ApiOperation(value = "分页课程列表")
    @PostMapping("{page}/{limit}")
    public Result pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody CourseQuery courseQuery){

        Page<EduCourse> pageParam = new Page<>(page, limit);
        courseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  Result.ok().data("total", total).data("rows", records);
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation("根据课程Id删除课程")
    public  Result deleteCourseById(@PathVariable("id") String id)
    {
       boolean deleteCourse=courseService.deleteCourse(id);
        if(deleteCourse)
        {
            return  Result.ok();
        }
        return  Result.error();
    }


}

