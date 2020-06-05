package com.guli.usercenter.mapper;

import com.guli.usercenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author hxq
 * @since 2020-03-27
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer getUserRegisterByDay(String day);

}
