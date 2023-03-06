package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) { this.fileMapper = fileMapper; }


    public boolean fileExists(String filename, Integer userId) {
        List<File> files = getAllFiles(userId);

        for(File file: files) {
            System.out.println(file.getFilename());
            System.out.println(filename);
            if(Objects.equals(file.getFilename(), filename)) {
                return true;
            }
        }

        return false;

    }

    public List<File> getAllFiles(Integer userId) {
        return fileMapper.getAllFiles(userId);
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    public Integer add(File file) {
        return fileMapper.addFile(file);
    }

    public boolean delete(Integer fileId) {
        return fileMapper.deleteFile(fileId);
    }

}
