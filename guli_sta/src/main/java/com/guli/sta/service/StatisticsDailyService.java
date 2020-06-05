package com.guli.sta.service;

import com.guli.sta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author hxq
 * @since 2020-03-27
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 生成统计数据
     * @param day
     * @return
     */
    boolean addStaData(String day);

    /**
     * 生成统计数据
     * @param item
     * @return
     */
    Map<String, Object> getStaData(Map<String, Object> item);
}
