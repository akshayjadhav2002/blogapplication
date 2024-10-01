package com.example.myblogapplication.services.servicesImpl;

import com.example.myblogapplication.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImplementation implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //fileName
        String fileName = file.getOriginalFilename();
        //random name generate file
        String randomID  = UUID.randomUUID().toString();
        String fileName1 = randomID.concat(fileName.substring(fileName.lastIndexOf('.')));

        //Full path
        String filePath = path + File.separator + fileName1 ;

        //create folder of not exits
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }
        //filecopy
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

        String fullpath = path+File.separator+fileName;
        InputStream isr = new FileInputStream(fullpath);
        return isr;
    }
}
