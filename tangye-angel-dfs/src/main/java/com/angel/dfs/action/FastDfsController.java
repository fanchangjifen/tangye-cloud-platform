package com.angel.dfs.action;

import com.angel.common.http.R;
import com.angel.dfs.entity.FileBean;
import com.angel.dfs.service.impl.FastDfsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("fdfs")
public class FastDfsController {

    @Autowired
    private FastDfsService fastDfsService;

    /**
     * 上传文件
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload/file")
    public R uploadFile(MultipartFile file){
        if (null == file) {
            return R.failed("file body is null.");
        }
        try {
            FileBean bean = fastDfsService.uploadFile(file);
            return R.ok(bean);
        } catch (Exception e) {
            //e.printStackTrace();
            return R.failed(e.getMessage());
        }
    }

    /**
     * 删除文件
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/delete/file")
    public R delete(@RequestParam("fileUrl") String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return R.failed("file url is null.");
        }
        try {
            fastDfsService.deleteFile(fileUrl);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed(e.getMessage());
        }
    }
}
