package com.notes_app.post_it_app.controller;

import com.notes_app.post_it_app.dto.NotesRequest;
import com.notes_app.post_it_app.dto.NotesResponse;
import com.notes_app.post_it_app.services.NotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notes")
public class NotesController {
    private final NotesService notesService;

    @GetMapping("/all/{id}")
    public ResponseEntity<NotesResponse> getAllNotes(@PathVariable Long id) {
        return new ResponseEntity<>(notesService.fetchAllNotes(id), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createNotes(@RequestBody NotesRequest freshNote) {
        NotesResponse note = notesService.addNotes(freshNote);

        if (note == null) {
            return new ResponseEntity<>("Note not saved! Trg again...", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editNote(@RequestHeader("X-User-Id") Long userId, @RequestBody NotesRequest editedNote) {
        // TODO: We'll use the userID to later verify the request's origin by validating the userId

        NotesResponse note = notesService.updateNote(editedNote);

        if (note == null) {
            return new ResponseEntity<>("Invalid Note ID", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(note, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeNote(@RequestHeader ("X-User-Id") Long userId, @PathVariable Long id) {
        // TODO: We'll use the userID to later verify the request's origin by validating the userId
        Boolean isRemoved = notesService.deleteNote(id);

        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
