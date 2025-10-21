package com.notes_app.post_it_app.services;

import com.notes_app.post_it_app.dto.NotesRequest;
import com.notes_app.post_it_app.dto.NotesResponse;
import com.notes_app.post_it_app.models.Notes;
import com.notes_app.post_it_app.repositories.NotesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotesService {
    private final NotesRepository notesRepository;

    public NotesResponse addNotes(NotesRequest notes) {
        Notes note = new Notes();

        note.setNote(notes.getNote());
        note.setName(notes.getName());
        notesRepository.save(note);
        NotesResponse notesResponse = new NotesResponse();
        notesResponse.setNote(note.getNote());
        notesResponse.setName(notes.getName());

        return notesResponse;
    }

    public List<NotesResponse> fetchAllNotes() {
//        return notesRepository.findAll().stream().toList();
        List<Notes> notes = notesRepository.findAll();
        List<NotesResponse> res = new ArrayList<>();
        for (Notes note : notes) {
            NotesResponse noteResponse = new NotesResponse();
            noteResponse.setName(note.getName());
            noteResponse.setNote(note.getNote());
            res.add(noteResponse);
        }
        return res;
    }

    public NotesResponse updateNote(NotesRequest note) {
        return notesRepository.findById(note.getId()).map(matchingNote -> {
            matchingNote.setName(note.getName());
            matchingNote.setNote(note.getNote());
            notesRepository.save(matchingNote);
            return mapToNoteResponse(matchingNote);
        }).orElse(null);
    }

    private NotesResponse mapToNoteResponse(Notes note) {
        NotesResponse notesResponse = new NotesResponse();

        notesResponse.setId(note.getId());
        notesResponse.setNote(note.getNote());
        notesResponse.setName(note.getName());

        return notesResponse;
    }

    public Boolean deleteNote(Long id) {
        Optional<Notes> note = notesRepository.findById(id);

        if (note.isEmpty()) {
            return false;
        }

        notesRepository.deleteById(id);

        return true;
    }
}
