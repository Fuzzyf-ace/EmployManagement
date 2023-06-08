package com.daiming.employmanagement.controller;

import com.daiming.employmanagement.model.Employer;
import com.daiming.employmanagement.model.Shift;
import com.daiming.employmanagement.service.EmployerService;
import com.daiming.employmanagement.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class ShiftController {
    @Autowired
    EmployerService employerService;

    @Autowired
    ShiftService shiftService;

    @GetMapping("/employee/getAllShifts")
    public List<Shift> getShiftsByEmployee(Principal principal) {
        return shiftService.getShiftsByEmployee(principal.getName());
    }

    @PostMapping("/employee/acceptShift")
    public void acceptShift(@RequestBody Shift shift, Principal principal) {
        shiftService.acceptShift(shift.getId(), principal.getName());
    }

    @GetMapping("/employee/getMyShifts")
    public List<Shift> getShiftsAcceptedByEmployee(Principal principal) {
        return shiftService.getShiftsAcceptedByEmployee(principal.getName());
    }

    @PutMapping("/employer/addShift")
    public void addShift (@RequestBody Shift shift, Principal principal) {
        shiftService.addShift(shift, principal.getName());
    }

    @GetMapping("/employer/getShifts")
    public List<Shift> getShiftsByEmployer(Principal principal) {
        return shiftService.getShiftsByEmployer(principal.getName());
    }

    @PostMapping("/employer/updateShift")
    public void updateShift(@RequestBody Shift shift) {
        shiftService.updateShift(shift);
    }

    @DeleteMapping("/employer/deleteShift")
    public void deleteShift(@RequestBody Shift shift) {
        shiftService.deleteShift(shift.getId());
    }

}
