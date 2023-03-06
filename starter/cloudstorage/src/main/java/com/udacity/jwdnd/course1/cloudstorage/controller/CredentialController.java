package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialController {

    private EncryptionService encryptionService;
    private UserService userService;
    private CredentialService credentialService;

    public CredentialController(EncryptionService encryptionService, UserService userService, CredentialService credentialService) {
        this.encryptionService = encryptionService;
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @GetMapping("/credentials/delete")
    public String deleteCredential(@RequestParam String credentialId, Model model, RedirectAttributes redirectAttributes) {
        int parsedCredentialId = Integer.parseInt(credentialId);

        try {
            boolean success = credentialService.deleteCredential(parsedCredentialId);

            if (success) {
                redirectAttributes.addFlashAttribute("message", "Delete the credential successfully");
            } else {
                redirectAttributes.addFlashAttribute("message", "Could not delete the credential!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message",
                    "Could not delete the credential. Error: " + e.getMessage());
        }

        return "redirect:/home";
    }

    @PostMapping("/addCredential")
    public String addCredential(Model model, Credential credential, Authentication authentication, RedirectAttributes redirectAttributes) {

        String encryptionKey;
        String password = credential.getPassword();
        String username = authentication.getName();
        String encryptedValue;


        User user = userService.getUser(username);
        Integer userId = user.getUserId();
        credential.setUserid(userId);

        String credentialid = credential.getCredentialId();

        if(credentialid != "") {
            encryptionKey = credentialService.getEncryptionKey(credential);
            encryptedValue = encryptionService.encryptValue(password,encryptionKey);
            credential.setPassword(encryptedValue);
            credentialService.editCredential(credential);

            try {
                boolean success = credentialService.editCredential(credential);


                if(success) {
                    redirectAttributes.addFlashAttribute("message", "Edited the credential successfully");
                } else {
                    redirectAttributes.addFlashAttribute("message", "Could not edit the credential!");
                }
            } catch(Exception e) {
                redirectAttributes.addFlashAttribute("message",
                        "Could not edit the credential. Error: " + e.getMessage());
            }

        } else {
            encryptionKey = RandomStringUtils.randomAlphanumeric(16);
            encryptedValue = encryptionService.encryptValue(password,encryptionKey);
            credential.setPassword(encryptedValue);
            credential.setKey(encryptionKey);


            try {
                Integer id = credentialService.addCredential(credential);


                if(id > 0) {
                    redirectAttributes.addFlashAttribute("message", "Upload the credential successfully");
                } else {
                    redirectAttributes.addFlashAttribute("message", "Could not upload the credential!");
                }
            } catch(Exception e) {
                redirectAttributes.addFlashAttribute("message",
                        "Could not save the credential. Error: " + e.getMessage());
            }
        }

        return "redirect:/home";
    }
}
