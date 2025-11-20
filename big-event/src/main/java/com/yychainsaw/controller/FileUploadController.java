package com.yychainsaw.controller;

import com.yychainsaw.pojo.Result;
import com.yychainsaw.utils.AliOssUtil;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    
    @SneakyThrows
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        try {
            String originalFilename = file.getOriginalFilename();

            //保证文件名唯一，可以使用UUID或者时间戳等方式
            String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

            //file.transferTo(new File("C:\\Users\\YYchainsaw\\IdeaProjects\\files\\" + filename));

            String url = AliOssUtil.uploadFile(filename, file.getInputStream());
            return Result.success(url);
        } catch (Exception e) {
            return Result.error("文件上传失败" + e.getMessage());
        }
    }
}
