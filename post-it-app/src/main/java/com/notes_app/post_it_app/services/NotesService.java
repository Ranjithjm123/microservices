package com.notes_app.post_it_app.services;

import com.notes_app.post_it_app.dto.NotesRequest;
import com.notes_app.post_it_app.dto.NotesResponse;
import com.notes_app.post_it_app.models.Notes;
import com.notes_app.post_it_app.repositories.NotesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotesService {
    private final NotesRepository notesRepository;

    public NotesResponse addNote(NotesRequest notesRequest) {
        Notes note = new Notes();
        note.setNote(notesRequest.getNote());
        note.setName(notesRequest.getName());
        
        Notes savedNote = notesRepository.save(note);
        return mapToNoteResponse(savedNote);
    }

    public Optional<NotesResponse> fetchNoteById(Long id) {
        return notesRepository.findById(id).map(this::mapToNoteResponse);
    }

    public Optional<NotesResponse> updateNote(Long id, NotesRequest notesRequest) {
        return notesRepository.findById(id).map(existingNote -> {
            existingNote.setName(notesRequest.getName());
            existingNote.setNote(notesRequest.getNote());
            Notes updatedNote = notesRepository.save(existingNote);
            return mapToNoteResponse(updatedNote);
        });
    }

    private NotesResponse mapToNoteResponse(Notes note) {
        NotesResponse notesResponse = new NotesResponse();
        notesResponse.setId(note.getId());
        notesResponse.setNote(note.getNote());
        notesResponse.setName(note.getName());
        notesResponse.setCreatedAt(note.getCreatedAt());
        notesResponse.setUpdatedAt(note.getUpdatedAt());
        return notesResponse;
    }

    public boolean deleteNote(Long id) {
        if (notesRepository.existsById(id)) {
            notesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
