package com.daiming.employmanagement.controller;

import com.daiming.employmanagement.model.Employer;
import com.daiming.employmanagement.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployerController {
    @Autowired
    EmployerService userService;
    @PostMapping("/signup")
    public String signup (@RequestBody Employer employer) {
        userService.addEmployer(employer);
        return employer.getFirstName();
    }
}
