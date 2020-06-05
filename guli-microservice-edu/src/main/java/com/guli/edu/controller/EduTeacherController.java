package com.guli.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.EduTeacher;
import com.guli.edu.service.CourseService;
import com.guli.edu.service.EduTeacherService;
import com.guli.result.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author hxq
 * @since 2020-03-31
 */
@RestController
@RequestMapping("/eduTeacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "分页讲师列表")
    @GetMapping(value = "{page}/{limit}")
    public Result pageList(
@ApiParam(name = "page", value = "当前页码", required = true)
        @PathVariable Long page,
@ApiParam(name = "limit", value = "每页记录数", required = true)
        @PathVariable Long limit){
        Page<EduTeacher> pageParam = new Page<EduTeacher>(page, limit);
        Map<String, Object> map = teacherService.pageListWeb(pageParam);
        return  Result.ok().data(map);
    }


    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping(value = "{id}")
    public Result getById(
        @ApiParam(name = "id", value = "讲师ID", required = true)
        @PathVariable String id){
        //查询讲师信息
        EduTeacher teacher = teacherService.getById(id);
        //根据讲师id查询这个讲师的课程列表
        List<Course> courseList = courseService.selectByTeacherId(id);
        return Result.ok().data("teacher", teacher).data("courseList", courseList);
    }
}

