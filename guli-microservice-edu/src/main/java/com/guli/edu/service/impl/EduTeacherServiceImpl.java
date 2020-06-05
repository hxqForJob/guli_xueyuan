package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.EduTeacher;
import com.guli.edu.mapper.EduTeacherMapper;
import com.guli.edu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author hxq
 * @since 2020-03-31
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> pageListWeb(Page<EduTeacher> pageTeacher) {
        //调用方法分页查询，把分页数据封装到pageTeacher对象里面
        baseMapper.selectPage(pageTeacher, null);

        //把pageTeacher对象里面分页数据获取出来，封装到map集合中
        long current = pageTeacher.getCurrent(); //当前页
        long pages = pageTeacher.getPages(); //总页数
        long size = pageTeacher.getSize(); //每页显示记录数
        long total = pageTeacher.getTotal(); //总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //每页数据list集合
        boolean hasPrevious = pageTeacher.hasPrevious(); //上一页
        boolean hasNext = pageTeacher.hasNext(); //下一页

        //封装map
        Map<String,Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
