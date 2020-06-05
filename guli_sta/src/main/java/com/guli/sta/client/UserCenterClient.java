package com.guli.sta.client;


import com.guli.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户会员管理微服务
 */
@Component
@FeignClient("guli-usercenter")
public interface UserCenterClient {

    @GetMapping("/usercenter/getRegisterSta")
     Result getRegisterStaticDay(@RequestParam("day") String day);
}
