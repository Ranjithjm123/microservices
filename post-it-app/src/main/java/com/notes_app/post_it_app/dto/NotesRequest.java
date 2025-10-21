package com.notes_app.post_it_app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotesRequest {
    private Long id;
    private String name;
    private String Note;
}
