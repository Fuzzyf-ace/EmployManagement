package com.daiming.employmanagement.controller;

import com.daiming.employmanagement.model.Employer;
import com.daiming.employmanagement.model.Token;
import com.daiming.employmanagement.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployerController {
    @Autowired
    EmployerService employerService;


    @PostMapping("/employer/signup")
    public String signup (@RequestBody Employer employer) {
        employerService.addEmployer(employer);
        return employer.getFirstName();
    }
    @PostMapping("/employer/login")
    public Token login(@RequestBody Employer employer) {
        return employerService.employerLogin(employer);
    }
}
