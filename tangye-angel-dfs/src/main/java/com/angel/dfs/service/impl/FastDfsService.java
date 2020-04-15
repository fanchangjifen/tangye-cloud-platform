package com.angel.dfs.service.impl;

import com.angel.common.utils.CTools;
import com.angel.dfs.entity.FileBean;
import com.angel.dfs.entity.StorePath;
import com.angel.dfs.service.DfsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;

@Slf4j
@Service
public class FastDfsService implements DfsService {

    private static final String CONF_NAME = "fdfs_client.conf";

    private StorageClient storageClient = null;

    private TrackerServer trackerServer = null;

    public void initStorageClient() throws Exception {
        log.info("init connection");
        ClientGlobal.init(CONF_NAME);
        TrackerClient tracker = new TrackerClient();
        trackerServer = tracker.getTrackerServer();
        StorageServer storageServer = null;
        storageClient = new StorageClient(trackerServer, storageServer);
    }

    public void closeClient() {
        log.info("close connection");
        if(storageClient != null){
            try {
                storageClient.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public FileBean uploadFile(MultipartFile file) throws Exception {
        this.initStorageClient();
        NameValuePair[] metaList = new NameValuePair[1];
        metaList[0] = new NameValuePair("fileName", file.getName());
        String[] result = storageClient.upload_file(file.getBytes(),FilenameUtils.getExtension(file.getOriginalFilename()),metaList);
        log.info("result {}", Arrays.asList(result));
        this.closeClient();
        if (result.length == 2) {
            StorePath storePath = new StorePath(result[0],result[1]);
            FileBean bean = new FileBean();
            bean.setId(CTools.getSnowflakeId());
            bean.setDate(new Date());
            bean.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
            bean.setGroup(storePath.getGroup());
            bean.setPath(storePath.getPath());
            bean.setFullPath(storePath.getFullPath());
            bean.setName(file.getOriginalFilename());
            bean.setMd5(DigestUtils.md5Hex(file.getInputStream()));
            bean.setLength(file.getSize());
            return bean;
        }else{
            throw new RuntimeException("upload file error");
        }
    }

    @Override
    public FileBean uploadFile(String content, String fileExtension) throws Exception {
        return null;
    }

    @Override
    public void deleteFile(String fileUrl) throws Exception {
        this.initStorageClient();
        StorePath storePath = StorePath.praseFromUrl(fileUrl);
        storageClient.delete_file(storePath.getGroup(),storePath.getPath());
        log.info("{} delete success",fileUrl);
        this.closeClient();
    }
}
