package com.guli.vod.util;

import com.aliyun.oss.ClientException;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import com.google.common.collect.Lists;
import com.guli.vod.entity.VideoInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public class VoUtil {

    /**
     * 获取Vod客户端端
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     * @throws ClientException
     */
    private  static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * 流式上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     * @param inputStream
     */
    private static String UploadStream(String accessKeyId, String accessKeySecret, String title, String fileName, InputStream inputStream) {
        String videoId=null;
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()){
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
        videoId=response.getVideoId();
        return  videoId;
    }

    /**
     * 流式上传接口
     *
     * @param title
     * @param fileName
     * @param inputStream
     */
    public  static String UploadStream(String title,String fileName, InputStream inputStream)
    {
       return UploadStream(ConstantPropertiesUtil.ACCESS_KEY_ID,ConstantPropertiesUtil.ACCESS_KEY_SECRET,title,fileName,inputStream);
    }


    /**
     * 批量删除video
     * @param videoIds
     */
    public static void  deleteVideo(List<String> videoIds) throws Exception {
        if(videoIds==null||videoIds.size()==0) return;
        DefaultAcsClient client = initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        deleteVideo(client,videoIds);
    }

    /**
     * 删除一个video
     * @param videoId
     */
    public static void  deleteVideo(String videoId) throws Exception {
        if(StringUtils.isEmpty(videoId))return;
        DefaultAcsClient client = initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        List<String> videoIds=Lists.newArrayList(videoId);
        deleteVideo(client,videoIds);

    }

    /**
            * 删除视频
     * @param client 发送请求客户端
     * @return DeleteVideoResponse 删除视频响应数据
     * @throws Exception
     */
    private static DeleteVideoResponse deleteVideo(DefaultAcsClient client,List<String> videoIds) throws Exception {
        DeleteVideoRequest request = new DeleteVideoRequest();
        StringBuffer videosID=new StringBuffer();
        if(videoIds!=null) {
            for (int i = 0; i < videoIds.size(); i++) {
                if (i == videoIds.size()) {
                    videosID.append(videoIds.get(i));
                } else {
                    videosID.append(videoIds.get(i)).append(",");
                }
            }
        }
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(videosID.toString());
        return client.getAcsResponse(request);
    }

    /*获取播放地址函数*/
    private static GetVideoPlayAuthResponse    getPlayInfo(DefaultAcsClient client, String videoId) throws Exception {
        GetVideoPlayAuthRequest  request = new GetVideoPlayAuthRequest ();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }
    /**
     * 获取播放凭证和播放连接
     */
    public  static VideoInfo getUploadAuth(String videoId) throws Exception {
        VideoInfo videoInfo=new VideoInfo();
        videoInfo.setVideoId(videoId);
        DefaultAcsClient client = initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        GetVideoPlayAuthResponse   response;

        try {
            response = getPlayInfo(client,videoId);
            //播放地址
            videoInfo.setPlayAuth(response.getPlayAuth());
            //RequestId 信息
            videoInfo.setRequestId(response.getRequestId());
            //meta信息
            videoInfo.setVideoMetaTitle(response.getVideoMeta().getTitle());
        } catch (Exception e) {
            e.printStackTrace();
            videoInfo.setErrorMessage(e.getLocalizedMessage());
        }
        return  videoInfo;


    }



}
