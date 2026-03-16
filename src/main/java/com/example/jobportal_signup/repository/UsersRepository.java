package com.example.jobportal_signup.repository;

import com.example.jobportal_signup.models.Users;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Id> {
}
