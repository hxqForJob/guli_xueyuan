package com.guli.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.guli.oss.service.FileService;
import com.guli.oss.util.ConstantPropertiesUtil;
import com.guli.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@Service
@Slf4j
public class FileServiceImpl implements FileService {
    private static final String TYPESTR[] = {".png", ".jpg", ".bmp", ".gif", ".jpeg"};

    @Override
    public String uploadFileToOss(MultipartFile file) {
        String url = null;
        OSSClient ossClient = null;
        try {
            // Endpoint以杭州为例，其它Region请按实际情况填写。
            String endpoint = ConstantPropertiesUtil.END_POINT;
            // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
            String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
            // 创建OSSClient实例。
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            //判断文件类型
            boolean flag = false;
            //判断文件格式
            for (String type : TYPESTR) {
                if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return "图片格式不正确";
            }
            //判断文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                return "文件内容不正确";
            }

            String DatePath = new DateTime().toString("yyyy/MM/dd");
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = ConstantPropertiesUtil.FILE_HOST + "/" + DatePath + "/" + UUID.randomUUID().toString() + ext;
            ;
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(ConstantPropertiesUtil.BUCKET_NAME, fileName, inputStream);
            url = "https://" + ConstantPropertiesUtil.BUCKET_NAME + "." + ConstantPropertiesUtil.END_POINT + "/" + fileName;
        } catch (IOException e) {
            url = "上传失败";
            //写入日志
            log.error(ExceptionUtil.getMessage(e));
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}
