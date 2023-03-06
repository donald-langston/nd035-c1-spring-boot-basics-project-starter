package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {

        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/notes/delete")
    public String deleteNote(@RequestParam String noteId, Model model, RedirectAttributes redirectAttributes) {
        int parsedNoteId = Integer.parseInt(noteId);

        try {
            boolean success = noteService.deleteNote(parsedNoteId);

            if (success) {
                redirectAttributes.addFlashAttribute("message", "Delete the note successfully");
            } else {
                redirectAttributes.addFlashAttribute("message", "Could not delete the note!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message",
                    "Could not delete the note. Error: " + e.getMessage());
        }

        return "redirect:/home";
    }

    @PostMapping("/addNote")
    public String addNote(Authentication authentication, Note note, Model model, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userId = user.getUserId();
        note.setUserid(userId);

        String noteid = note.getNoteId();

        if(noteid != "") {

            try {
                boolean success = noteService.editNote(note);


                if(success) {
                    redirectAttributes.addFlashAttribute("message", "Edited the note successfully");
                } else {
                    redirectAttributes.addFlashAttribute("message", "Could not edit the note!");
                }
            } catch(Exception e) {
                redirectAttributes.addFlashAttribute("message",
                        "Could not edit the note. Error: " + e.getMessage());
            }

        } else {

            try {
                Integer id = noteService.addNote(note);


                if(id > 0) {
                    redirectAttributes.addFlashAttribute("message", "Upload the note successfully");
                } else {
                    redirectAttributes.addFlashAttribute("message", "Could not upload the note!");
                }
            } catch(Exception e) {
                redirectAttributes.addFlashAttribute("message",
                        "Could not save the note. Error: " + e.getMessage());
            }
        }

       return "redirect:/home";
    }
}
