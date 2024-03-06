package com.thqu1et.e_commerces.repository;

import com.thqu1et.e_commerces.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

