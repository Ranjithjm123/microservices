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

    @GetMapping("/all")
    public List<NotesResponse> getAllNotes() {
        return notesService.fetchAllNotes();
    }

    @PostMapping("/new")
    public ResponseEntity<?> createNotes(@RequestBody NotesRequest freshNote) {
        NotesResponse note = notesService.addNotes(freshNote);

        if (note == null) {
            return new ResponseEntity<>("Note not saved! Trg again...", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(note, HttpStatus.CREATED);

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
