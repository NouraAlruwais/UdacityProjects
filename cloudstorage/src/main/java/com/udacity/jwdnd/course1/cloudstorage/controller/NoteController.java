package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping()
    public String homeView(@ModelAttribute("credential") Credential credential, @ModelAttribute("note") Note note, Model model, Authentication authentication) {
        int userId=this.userService.getUserid(authentication.getName());
        model.addAttribute("getNote",this.noteService.getAllNotes(userId));

        return "home";

    }

    @PostMapping("/addNote")
    public String addNote(@ModelAttribute("credential") Credential credential, @ModelAttribute("note") Note note, Authentication authentication, Model model){

        if (note.getNoteId()==null){
        this.noteService.addNote(note, authentication.getName());
        }
        else{
            this.noteService.UpdateNote(note);
        }
        int userId=this.userService.getUserid(authentication.getName());
        model.addAttribute("getNote",this.noteService.getAllNotes(userId));
        model.addAttribute("changesSuccess",true);

        return "result";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable(name = "noteId") Integer noteId,Model model){
        this.noteService.deleteNote(noteId);
        model.addAttribute("changesSuccess",true);

        return "result";
    }
}
