package com.notes_app.post_it_app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotesRequest {
    private Long id;
    
    @NotBlank(message = "Note name cannot be blank")
    private String name;
    
    @NotBlank(message = "Note content cannot be blank")
    private String note;
}
