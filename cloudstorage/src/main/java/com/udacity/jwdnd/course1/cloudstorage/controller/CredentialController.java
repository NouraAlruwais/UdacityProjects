package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;
    private final NoteService noteService;
    private final FileService fileService;
    public String decryptedPassword=null;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService, NoteService noteService, FileService fileService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
        this.noteService = noteService;
        this.fileService = fileService;
    }

    @GetMapping()
    public String homeView(@ModelAttribute("credential") Credential credential, @ModelAttribute("note") Note note, Model model, Authentication authentication) {
        int userId=this.userService.getUserid(authentication.getName());
        model.addAttribute("cred",this.credentialService.getCredential(userId));
        model.addAttribute("decryptedPassword",decryptedPassword);
        model.addAttribute("getNote",this.noteService.getAllNotes(userId));
        model.addAttribute("getFile",this.fileService.getAllFiles(userId));

        return "home";
    }
    @PostMapping("/addCredential")
    public String addCredentials(@ModelAttribute("credential") Credential credential, @ModelAttribute("note") Note note, Authentication authentication, Model model){
        SecureRandom random=new SecureRandom();
        byte[] key= new byte[16];
        random.nextBytes(key);
        String encodedKey= Base64.getEncoder().encodeToString(key);
        String encryptedPassword= encryptionService.encryptValue(credential.getPassword(),encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setKey(encodedKey);
        decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
        model.addAttribute("decryptedPassword",decryptedPassword);

        if(credential.getCredentialId()==null){
            this.credentialService.addCredential(credential,authentication.getName());}

        else {
            this.credentialService.updateCredential(credential);

        }
        int userId=this.userService.getUserid(authentication.getName());
        model.addAttribute("cred",this.credentialService.getCredential(userId));
        model.addAttribute("changesSuccess",true);

        return "result";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable(name = "credentialId") Integer credentialId, Model model){
        this.credentialService.deleteCredential(credentialId);
        model.addAttribute("changesSuccess",true);

        return "result";
    }
}
