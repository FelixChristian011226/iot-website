package com.felix.iotbackend.controller;

import com.felix.iotbackend.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        //SAVE FILE TO LOCAL DISK
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        String property = System.getProperty("user.dir");   //GET CURRENT WORKING DIRECTORY
//        String path = (new File(property)).getParent()+File.separator+"iot-database"+File.separator+"files";    //.\iot-website\iot-database\files
        String path = (new File(property)).getParent()+File.separator+"iot-frontend"+File.separator+"vue-project"+File.separator+"public"+File.separator+"avatars";
        file.transferTo(new File(path+File.separator+filename));
        return Result.success(File.separator+"avatars"+File.separator+filename);
    }
}
