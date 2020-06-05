package com.guli.sta.controller;


import com.guli.result.Result;
import com.guli.sta.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author hxq
 * @since 2020-03-27
 */
@RestController
@RequestMapping("/sta")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService staService;

    /**
     * 生成统计数据
     * @param day
     * @return
     */
    @PostMapping("/addStaData/{day}")
    public Result addStaData(@PathVariable("day") String day)
    {
        boolean flg= staService.addStaData(day);
        if(flg)
            return  Result.ok();
        else
            return  Result.error();
    }

    /**
     * 查询统计数据
     * @param item
     * @return
     */
    @PostMapping("/getStaData")
    public Result getStaData(@RequestBody Map<String,Object> item)
    {
        Map<String,Object> data= staService.getStaData(item);
        return  Result.ok().data(data);
    }
}

