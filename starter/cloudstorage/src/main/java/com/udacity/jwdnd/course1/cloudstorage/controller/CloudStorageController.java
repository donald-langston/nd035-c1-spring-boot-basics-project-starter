package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller

public class CloudStorageController {

    private final FileService fileService;
    private final NoteService noteService;
    private final UserService userService;

    private final CredentialService credentialService;

    private final EncryptionService encryptionService;

    public CloudStorageController(FileService fileService, NoteService noteService, UserService userService, CredentialService credentialService, EncryptionService encryptionService) {

        this.fileService = fileService;
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }
    //File filelist = new File("/home/d/Documents/nd035-c1-spring-boot-basics-project-starter/starter/cloudstorage/src/main/resources/uploads/");
    //ArrayList<String> files = new ArrayList<String>(Arrays.asList(filelist.list()));
    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String getChatPage(Authentication authentication, Note note, Credential credential, Model model) {
        //ArrayList<String> files = fileService.getList();
        String username = authentication.getName();
        User user = userService.getUser(username);




        if(user != null) {
            Integer userId = user.getUserId();
            List<Note> notes = noteService.getNotes(userId);
            List<File> files = fileService.getAllFiles(userId);

            ArrayList<Credential> credentials = (ArrayList<Credential>) credentialService.getCredentials(userId);

            for(Credential cred : credentials) {
                cred.setDecryptedpswd(encryptionService.decryptValue(cred.getPassword(), cred.getKey()));
            }

            model.addAttribute("credentials", credentials);
            model.addAttribute("notes", notes);
            model.addAttribute("files", files);
        }

        //List<Note> notes = noteService.getNotes(note);

        //System.out.println(notes);

        //model.addAttribute("notes", notes);


        //noteService.getNotes(userId);


        //model.addAttribute("files", files);

        return "home";
    }
}
