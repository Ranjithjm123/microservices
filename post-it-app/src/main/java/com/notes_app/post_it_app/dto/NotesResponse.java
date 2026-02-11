package com.notes_app.post_it_app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NotesResponse {
    private Long id;
    private String name;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
