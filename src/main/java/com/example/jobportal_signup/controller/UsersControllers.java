package com.example.jobportal_signup.controller;

import com.example.jobportal_signup.models.Users;
import com.example.jobportal_signup.repository.UsersRepository;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/users")
public class UsersControllers {
    private UsersRepository usersRepository;
    public UsersControllers(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @PostMapping("/create")
    public Users create(@RequestBody Users users) {
        System.out.println("Signup API called");

        System.out.println(users.getName());
        System.out.println(users.getUsername());
        return usersRepository.save(users);
    }
}
