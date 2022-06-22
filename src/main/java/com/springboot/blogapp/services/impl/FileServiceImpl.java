package com.springboot.blogapp.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.blogapp.services.FileService;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(String path, MultipartFile multipartFile) throws IOException {
        //File name
        String name = multipartFile.getOriginalFilename();
        //random name generate file
        String randomID = UUID.randomUUID().toString();
        String filename1 = randomID.concat(name.substring(name.lastIndexOf(".")));
        //Fullpath
        String filePath = path + File.separator + filename1;
        //create folder if not created
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        //copy file
        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));
        return filename1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path+File.separator+fileName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
    
}
