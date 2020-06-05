package com.guli.teacher.controller;


import com.guli.result.Result;
import com.guli.teacher.entity.EduChapter;
import com.guli.teacher.entity.vo.ChapterVo;
import com.guli.teacher.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(description ="课程章节管理")
@RequestMapping("/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 根据课程id获取发布的章节和小节控制器
     * @param courseId
     * @return
     */
    @ApiOperation("根据课程id获取发布的章节和小节")
    @GetMapping("/getChapterByCourseId/{id}")
    public Result getChapterByCourseId(@PathVariable("id") String courseId)
    {
      List<ChapterVo> chapterVoes= eduChapterService.getChapterAndVideoByCourseId(courseId);
        if(chapterVoes.size()>0)
        {
            return Result.ok().data("list",chapterVoes);
        }
        return  Result.ok().data("list",null);
    }
    @ApiOperation("添加或编辑章节")
    @PostMapping("/addOrUpateChapter")
    public  Result addChapter(@RequestBody EduChapter chapter)
    {
        boolean flag= eduChapterService.cusSaveOrUpdate(chapter);
       if(flag)
       {
           return  Result.ok();
       }
       return  Result.error();
    }

    @ApiOperation("删除章节")
    @DeleteMapping("/deleteChapter/{id}")
    public  Result deleteChapter(@PathVariable("id") String id)
    {
      boolean flag=  eduChapterService.cusDeleteChapter(id);
      if(flag)
      {
          return Result.ok();
      }
       return  Result.error();
    }

    @ApiOperation("根据id查询章节")
    @GetMapping("/getChapter/{id}")
    public  Result getChapter(@PathVariable("id") String id)
    {
        EduChapter eduChapter=  eduChapterService.getById(id);
        if(eduChapter!=null)
        {
            return Result.ok().data("item",eduChapter);
        }
        return Result.error();
    }

}

