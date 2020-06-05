package com.guli.usercenter.service;

import com.guli.usercenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author hxq
 * @since 2020-03-27
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    Integer getUserRegister(String day);
}
