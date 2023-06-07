package com.daiming.employmanagement.controller;

import com.daiming.employmanagement.model.Employee;
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
    @PutMapping("/employer/addEmployee")
    public void addEmployee(@RequestBody Employee employee, Principal principal) {
        System.out.println("principal: " + principal.getName());
        System.out.println("employee: " + employee.toString());
        employeeService.addEmployee(employee, principal.getName());
    }

    @DeleteMapping("/employer/deleteEmployee")
    public void deleteEmployee(@RequestBody Employee employee) {

    }

    @PostMapping("/employer/updateEmployee")
    public void updateEmployee(@RequestBody Employee employee) {

    }

    @GetMapping("/employer/getEmployees")
    public List<Employee> getEmployees(Principal principal) {
//        Pageable pageable = PageRequest.of(0, 5);
        List<Employee> employees = employeeService.getEmployeesByEmployer(principal.getName());
//        List<Employee> employees = employeesPage.getContent();
        return employees;
    }
}
