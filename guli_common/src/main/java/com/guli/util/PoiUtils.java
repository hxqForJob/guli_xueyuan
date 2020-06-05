package com.guli.util;



import com.google.common.collect.Lists;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.util.StringUtil;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class PoiUtils {
    /**
     * 判断文件名是否为EXCEL文件
     * @param fileName
     * @return
     */
    public static final boolean checkExtensions(String fileName)
    {   int dotIndex= fileName.lastIndexOf(".");
        if(dotIndex==-1)
        {
            return  false;
        }
        String ext=fileName.substring(dotIndex);
        return Lists.newArrayList(".xls",".xlsx",".XLS",".XLSX").contains(ext);
    }

    /**
     * 判断excel文件内容
     * @param inputStream
     * @return
     */
    public static boolean isExeclFile(InputStream inputStream){

        boolean res = false;
        try {

            FileMagic fileMagic = FileMagic.valueOf(new BufferedInputStream(inputStream));
            if(Objects.equals(fileMagic, FileMagic.OLE2) || Objects.equals(fileMagic, FileMagic.OOXML)){
                res = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
