package com.notes_app.post_it_app.controller;

import com.notes_app.post_it_app.dto.NotesRequest;
import com.notes_app.post_it_app.dto.NotesResponse;
import com.notes_app.post_it_app.repositories.NotesRepository;
import com.notes_app.post_it_app.services.NotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<NotesResponse> createNotes(@RequestBody NotesRequest notes){
        NotesResponse newNotes = notesService.addNotes(notes);

        if(newNotes != null)
            return new ResponseEntity<>(newNotes, HttpStatus.CREATED);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
