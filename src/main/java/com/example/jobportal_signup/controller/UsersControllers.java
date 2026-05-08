package com.example.jobportal_signup.controller;

import com.example.jobportal_signup.models.Users;
import com.example.jobportal_signup.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/users")
public class UsersControllers {
    private UsersRepository usersRepository;
    public UsersControllers(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> create(@RequestBody Users users) {
        Users existingUser = usersRepository.findByEmail(users.getEmail());

        if (existingUser != null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Email already exists");
        }

        Users savedUser = usersRepository.save(users);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users users) {

        Users existingUser = usersRepository.findByEmail(users.getEmail());

        if(existingUser == null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Email not Found");
        }

        if(!existingUser.getPassword().equals(users.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid Password");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Login Successfully");
    }

}
