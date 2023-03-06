package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller

public class FileController {
    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {

        this.fileService = fileService;
        this.userService = userService;
    }


    @GetMapping("/files/view")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileid) throws Exception {
        Integer parsedFileId = Integer.parseInt(fileid);
        File dbFile = fileService.getFile(parsedFileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFilename() + "\"")
                .body(new ByteArrayResource(dbFile.getFiledata()));
    }

    @GetMapping("/files/delete")
    public String deleteFile(@RequestParam String fileid, Model model, RedirectAttributes redirectAttributes) {
        Integer parsedFileId = Integer.parseInt(fileid);
        File file = fileService.getFile(parsedFileId);
        String fileName = file.getFilename();

        try {
            boolean exists = fileService.delete(parsedFileId);

            if (exists) {
                redirectAttributes.addFlashAttribute("message", "Delete the file successfully: " + fileName);
            } else {
                redirectAttributes.addFlashAttribute("message", "The file does not exist!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message",
                    "Could not delete the file: " + fileName + ". Error: " + e.getMessage());
        }


        return "redirect:/home";
    }

    @PostMapping("/file-upload")
    public String handleFileUpload(@RequestParam("fileUpload")MultipartFile fileUpload, File file,
                                   Model model, RedirectAttributes redirectAttributes,
                                   Authentication authentication) throws IOException {

        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userId = user.getUserId();
        String fileName = fileUpload.getOriginalFilename();
        file.setUserid(userId);
        file.setFilename(fileName);
        file.setFilesize(String.valueOf(fileUpload.getSize()));
        file.setContenttype(fileUpload.getContentType());
        file.setFiledata(fileUpload.getBytes());



        if(fileService.fileExists(fileName, userId)) {
            redirectAttributes.addFlashAttribute("message", "File with name " + fileName + " already exists. Please choose another file.");
            return "redirect:/home";
        }

        try {
            Integer fileid = fileService.add(file);


            if(fileid > 0) {
                redirectAttributes.addFlashAttribute("message", "Upload the file successfully: " + fileName);
            } else {
                redirectAttributes.addFlashAttribute("message", "Could not upload the file: " + fileName + "!");
            }
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("message",
                    "Could not save the file: " + fileName + ". Error: " + e.getMessage());
        }

        return "redirect:/home";
    }
}
