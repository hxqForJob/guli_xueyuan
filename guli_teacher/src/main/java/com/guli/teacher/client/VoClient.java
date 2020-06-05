package com.guli.teacher.client;

import com.guli.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * guli-vod微服务
 */
@FeignClient("guli-vod")
@Component
public interface VoClient {
    /**
     * 根据videoId删除video微服务
     * @param videoId
     * @return
     */
    @DeleteMapping("/uploadVideo/deleteVideo/{videoId}")
     Result removeVideo(@PathVariable("videoId") String videoId);

    /**
     * 删除多个video微服务
     * @param videoIds
     * @return
     */
    @DeleteMapping("/uploadVideo/deleteVideos")
    Result removeVideos(@RequestParam("videoIdList") List<String> videoIds);

}
