package com.example.jobportal_signup.controller;


import com.example.jobportal_signup.models.Contact;
import com.example.jobportal_signup.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/contact")

public class ContactController {

    private ContactRepository contactRepository;
    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Contact contact) {
        Contact savedContact = contactRepository.save(contact);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedContact);
    }
}
