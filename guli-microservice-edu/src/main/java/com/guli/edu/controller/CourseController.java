package com.guli.edu.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.vo.ChapterDto;
import com.guli.edu.entity.vo.CourseWebVo;
import com.guli.edu.service.CourseService;
import com.guli.edu.service.EduChapterService;
import com.guli.result.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author hxq
 * @since 2020-03-31
 */
@RestController
@RequestMapping("/eduCourse")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation(value = "分页课程列表")
    @GetMapping(value = "{page}/{limit}")
    public Result pageList(
        @ApiParam(name = "page", value = "当前页码", required = true)
        @PathVariable Long page,
        @ApiParam(name = "limit", value = "每页记录数", required = true)
        @PathVariable Long limit){
        Page<Course> pageParam = new Page<Course>(page, limit);
        Map<String, Object> map = courseService.pageListWeb(pageParam);
        return  Result.ok().data(map);
    }



    //根据课程id查询课程详情信息
    @GetMapping("getFrontCourseInfo/{id}")
        public  Result getFrontCourseInfo(@PathVariable("id") String id) {
        //1 根据id查询课程基本信息
        CourseWebVo courseFrontInfo = courseService.getCourseInfoFrontId(id);

        //2 根据课程id查询课程大纲（章节和小节）
        List<ChapterDto> allChapterVideo = chapterService.getChapterAndVideoByCourseId(id);

        return Result.ok().data("courseFrontInfo",courseFrontInfo).data("chapterVideoList",allChapterVideo);
    }

}

