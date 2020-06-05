package com.guli.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * 上传文件到Oss
     * @param file
     * @return
     */
   String uploadFileToOss(MultipartFile file);
}
