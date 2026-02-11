package com.notes_app.post_it_app.controller;

import com.notes_app.post_it_app.dto.NotesRequest;
import com.notes_app.post_it_app.dto.NotesResponse;
import com.notes_app.post_it_app.services.NotesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notes")
public class NotesController {
    private final NotesService notesService;

    @GetMapping("/{id}")
    public ResponseEntity<NotesResponse> getNoteById(@PathVariable Long id) {
        return notesService.fetchNoteById(id)
                .map(note -> new ResponseEntity<>(note, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<NotesResponse> createNote(@Valid @RequestBody NotesRequest freshNote) {
        return new ResponseEntity<>(notesService.addNote(freshNote), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotesResponse> updateNote(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody NotesRequest editedNote) {
        // TODO: Validate that userId owns this note
        return notesService.updateNote(id, editedNote)
                .map(note -> new ResponseEntity<>(note, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {
        // TODO: Validate that userId owns this note
        boolean deleted = notesService.deleteNote(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) 
                      : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
