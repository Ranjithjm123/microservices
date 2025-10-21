package com.notes_app.post_it_app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotesResponse {
    private String name;
    private String Note;
}
