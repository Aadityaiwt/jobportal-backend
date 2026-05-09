package com.example.jobportal_signup.Services;


import com.example.jobportal_signup.models.Add;
import com.example.jobportal_signup.repository.AddRepository;
import org.springframework.stereotype.Service;

@Service

public class Services {
    private AddRepository addRepository;
    public Add saveAdd(Add add) {
        return addRepository.save(add);
    }
}
