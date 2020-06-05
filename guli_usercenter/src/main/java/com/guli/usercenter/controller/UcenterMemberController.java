package com.guli.usercenter.controller;


import com.guli.result.Result;
import com.guli.usercenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author hxq
 * @since 2020-03-27
 */
@RestController
@RequestMapping("/usercenter")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @GetMapping("/getRegisterSta")
    public Result getRegisterStaticDay(@RequestParam("day") String day)
    {
        Integer data = ucenterMemberService.getUserRegister(day);
        return  Result.ok().data("registerData",data);
    }


}

