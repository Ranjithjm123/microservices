package com.notes_app.user_app.repositories;

import com.notes_app.user_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByid(Long Id);
}


