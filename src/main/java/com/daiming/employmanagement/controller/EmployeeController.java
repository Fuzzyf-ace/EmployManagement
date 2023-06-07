package com.daiming.employmanagement.controller;

import com.daiming.employmanagement.model.Employee;
import com.daiming.employmanagement.model.Token;
import com.daiming.employmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/employee/login")
    public Token login(@RequestBody Employee employee) {
        return employeeService.employeeLogin(employee);
    }

    @PostMapping("/employee/updateEmployee")
    public void updateEmployeeByEmployee(@RequestBody Employee employee) {
        employeeService.updateEmployeeByEmployee(employee);
    }


}
