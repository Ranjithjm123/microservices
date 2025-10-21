package com.notes_app.post_it_app.repositories;

import com.notes_app.post_it_app.models.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {

}
