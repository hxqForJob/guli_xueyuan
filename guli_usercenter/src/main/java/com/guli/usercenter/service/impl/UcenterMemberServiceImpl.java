package com.guli.usercenter.service.impl;

import com.guli.result.ResultCode;
import com.guli.usercenter.entity.UcenterMember;
import com.guli.usercenter.exception.CusException;
import com.guli.usercenter.mapper.UcenterMemberMapper;
import com.guli.usercenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author hxq
 * @since 2020-03-27
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Override
    public Integer getUserRegister(String day) {
        Integer data = null;
        try {
            data = baseMapper.getUserRegisterByDay(day);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CusException(ResultCode.ERROR.getCode(),"程序错误请查看日志",e.getMessage());
        }
        return  data;
    }
}
