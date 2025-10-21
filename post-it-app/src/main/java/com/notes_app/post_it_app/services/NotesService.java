package com.notes_app.post_it_app.services;

import com.notes_app.post_it_app.dto.NotesRequest;
import com.notes_app.post_it_app.dto.NotesResponse;
import com.notes_app.post_it_app.models.Notes;
import com.notes_app.post_it_app.repositories.NotesRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotesService {
 private final NotesRepository notesRepository;

    public NotesResponse addNotes(NotesRequest notes){
        Notes note = new Notes();

        note.setNote(notes.getNote());
        note.setName(notes.getName());
        notesRepository.save(note);
        NotesResponse notesResponse = new NotesResponse();
        notesResponse.setNote(note.getNote());
        notesResponse.setName(notes.getName());

        return notesResponse;
    }

    //
    public List<NotesResponse> fetchAllNotes() {
//        return notesRepository.findAll().stream().toList();
        List<Notes> notes = notesRepository.findAll();
        List<NotesResponse> res = new ArrayList<>() ;
        for(Notes note : notes) {
            NotesResponse noteResponse = new NotesResponse();
            noteResponse.setName(note.getName());
            noteResponse.setNote(note.getNote());
            res.add(noteResponse);
        }
        return res;

    }
}
