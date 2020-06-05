package com.guli.teacher.controller;


import com.guli.result.Result;
import com.guli.teacher.entity.EduSubject;
import com.guli.teacher.entity.vo.SubjectVo;
import com.guli.teacher.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author hxq
 * @since 2020-03-20
 */
@Api(description = "课程分类管理模块")
@RestController
@RequestMapping("subject")
@CrossOrigin
public class EduSubjectController {

    //课程业务逻辑
    @Autowired
    private EduSubjectService subjectService;


    @PostMapping("/importSubject")
    @ApiOperation("导入课程")
    public  Result importSubject(
            @ApiParam(name = "file",value = "课程模板文件",required = true )
            MultipartFile file)
    {
        List<String> msg = null;
        msg = subjectService.importSubject(file);
        if (msg.size()>0)
        {
            return  Result.error().data("msg",msg);
        }
        else {
            return  Result.ok().data("msg","导入成功！");
        }
    }

    /**
     * 查询课程分类
     * @return
     */
    @GetMapping("/getSubjects")
    public Result getSujects(){
      List<SubjectVo> subjectVos=  subjectService.getSubjects();
      return  Result.ok().data("subjects",subjectVos);
    }

    /**
     * 根据Id查询子分类节点
     * @return
     */
    @GetMapping("/getChildrenSubject/{id}")
    public Result getChildrenSubject(@PathVariable("id") String id){
        List<String> msg=subjectService.getChildrenSubject(id);
        if(msg.size()>0)
        {
            return  Result.ok().data("chidren",msg);
        }else
        {
            return  Result.ok().data("chidren",null);
        }

    }
    /**
     * 删除课程分类
     * @return
     */
    @DeleteMapping("/deleteSubject/{id}")
    public Result deleteSubject(@PathVariable("id") String id){
    boolean flag=subjectService.deleteSubject(id);
        return flag? Result.ok():Result.error();
    }

    /**
     * 添加课程分类
     * @return
     */
    @PostMapping("/addSubject")
    public Result addSubject(@RequestBody EduSubject eduSubject){
        boolean flag=subjectService.addSubject(eduSubject);
        return flag? Result.ok():Result.error();
    }
}

