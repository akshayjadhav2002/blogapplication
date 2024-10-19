package com.example.crudAPI.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    // Ensure the upload directory exists
    public FileController() {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory
        }
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        Files.write(filePath, file.getBytes());
        return "File uploaded successfully!";
    }

    @GetMapping
    public String[] listFiles() {
        File folder = new File(UPLOAD_DIR);
        return folder.list();
    }

    @DeleteMapping("/{filename}")
    public String deleteFile(@PathVariable String filename) {
        File file = new File(UPLOAD_DIR + filename);
        if (file.delete()) {
            return "File deleted successfully!";
        } else {
            return "File not found!";
        }
    }
}
