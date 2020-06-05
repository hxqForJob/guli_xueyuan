package com.guli.sta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.result.Result;
import com.guli.result.ResultCode;
import com.guli.sta.client.UserCenterClient;
import com.guli.sta.entity.StatisticsDaily;
import com.guli.sta.exception.CusException;
import com.guli.sta.mapper.StatisticsDailyMapper;
import com.guli.sta.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author hxq
 * @since 2020-03-27
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    //会员微服务
    @Autowired
    private UserCenterClient userCenterClient;
    /**
     * 生成统计数据
     * @param day
     * @return
     */
    @Override
    public boolean addStaData(String day) {
        try {
            Result registerStaticDay = userCenterClient.getRegisterStaticDay(day);
            StatisticsDaily statisticsDaily=new StatisticsDaily();
            statisticsDaily.setRegisterNum((Integer) registerStaticDay.getData().get("registerData"));
            statisticsDaily.setCourseNum(RandomUtils.nextInt(1,200));
            statisticsDaily.setDateCalculated(day);
            statisticsDaily.setLoginNum(RandomUtils.nextInt(1,200));
            statisticsDaily.setVideoViewNum(RandomUtils.nextInt(1,200));
            statisticsDaily.setCourseNum(RandomUtils.nextInt(1,200));

            QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<StatisticsDaily>().eq("date_calculated", day);
            baseMapper.delete(wrapper);
            baseMapper.insert(statisticsDaily);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new CusException(ResultCode.ERROR.getCode(),"程序出错,请查看日志",e.getMessage());
        }

    }

    /**
     * 获取统计数据
     * @param item
     * @return
     */
    @Override
    public Map<String, Object> getStaData(Map<String, Object> item) {
        String beginDate=item.get("begin").toString();
        String endDate=item.get("end").toString();
        String type=item.get("type").toString();
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<StatisticsDaily>().between("date_calculated", beginDate, endDate);
        wrapper.select(type,"date_calculated");
        List<StatisticsDaily> dailyList = baseMapper.selectList(wrapper);
        List<String> dateList=new ArrayList<>();
        List<Integer> dataList=new ArrayList<>();

        try {
            for (StatisticsDaily sta: dailyList) {
                dateList.add(sta.getDateCalculated());
                switch (type) {
                    case "register_num":
                        dataList.add(sta.getRegisterNum());
                        break;
                    case "login_num":
                        dataList.add(sta.getLoginNum());
                        break;
                    case "video_view_num":
                        dataList.add(sta.getVideoViewNum());
                        break;
                    case "course_num":
                        dataList.add(sta.getCourseNum());
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw  new CusException(ResultCode.ERROR.getCode(),"程序错误，请查看日志",e.getMessage());
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("xData",dateList);
        map.put("yData",dataList);
        return  map;
    }
}
