package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final CredentialService credentialService;
    private final NoteService noteService;
    private final FileService fileService;
    private final UserService userService;
    private final CredentialController credentialController;

    public HomeController(CredentialService credentialService, NoteService noteService, FileService fileService, UserService userService, CredentialController credentialController) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.userService = userService;
        this.credentialController = credentialController;
    }

    @GetMapping()
    public String homeView(@ModelAttribute("credential") Credential credential, @ModelAttribute("note") Note note, Model model, Authentication authentication){
        int userId=this.userService.getUserid(authentication.getName());
        model.addAttribute("cred",this.credentialService.getCredential(userId));
        model.addAttribute("decryptedPassword", credentialController.decryptedPassword);
        model.addAttribute("getNote",this.noteService.getAllNotes(userId));
        model.addAttribute("getFile",this.fileService.getAllFiles(userId));

        return "home";
    }
}
