package com.angel.dfs.service;

import com.angel.dfs.entity.FileBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DfsService {

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public FileBean uploadFile(MultipartFile file) throws Exception;
    /**
     * 上传文本生成文件
     * @param content
     * @param fileExtension
     * @return
     */
    public FileBean uploadFile(String content, String fileExtension) throws Exception;
    /**
     * 根据URL删除文件
     * @param fileUrl
     */
    public void deleteFile(String fileUrl) throws Exception;

}
