package com.example.jobportal_signup.repository;

import com.example.jobportal_signup.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
