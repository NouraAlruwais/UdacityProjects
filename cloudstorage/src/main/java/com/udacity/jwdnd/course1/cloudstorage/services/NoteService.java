package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public int addNote(Note note, String username){
        int userId=userService.getUserid(username);
        return noteMapper.insert(new Note(null,note.getNoteTitle(),note.getNoteDescription(),userId));
    }

    public List<Note> getAllNotes(int userId){
        return noteMapper.getAllNotes(userId);
    }

    public void UpdateNote(Note note){
        noteMapper.update(note);
    }

    public void deleteNote(int noteId){
        noteMapper.delete(noteId);
    }
}
