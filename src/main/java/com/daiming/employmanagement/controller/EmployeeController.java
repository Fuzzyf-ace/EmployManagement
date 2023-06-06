package com.daiming.employmanagement.controller;

import com.daiming.employmanagement.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeController {

    @PutMapping("/addEmployee")
    public void addEmployee(Employee employee) {

    }

    @DeleteMapping("/deleteEmployee")
    public void deleteEmployee(Employee employee) {

    }

    @PostMapping("/updateEmployee")
    public void updateEmployee(Employee employee) {

    }

    @GetMapping("/getEmployees")
    public List<Employee> getEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        return employeeList;
    }
}
