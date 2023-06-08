package com.daiming.employmanagement.service;

import com.daiming.employmanagement.exception.EmployeeDoesNotExistException;
import com.daiming.employmanagement.exception.EmployerDoesNotExistException;
import com.daiming.employmanagement.exception.ShiftDoesNotExistException;
import com.daiming.employmanagement.model.Employee;
import com.daiming.employmanagement.model.Employer;
import com.daiming.employmanagement.model.Shift;
import com.daiming.employmanagement.repository.EmployeeRepository;
import com.daiming.employmanagement.repository.EmployerRepository;
import com.daiming.employmanagement.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShiftService {
    @Autowired
    ShiftRepository shiftRepository;

    @Autowired
    EmployerRepository employerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public void addShift(Shift shift, String employerEmail) {
        Employer employer = employerRepository.findByEmail(employerEmail);
        shift.setEmployer(employer);
        shiftRepository.save(shift);
    }
    public List<Shift> getShiftsByEmployer(String employerEmail) {
        Employer employer = null;
        try {
            employer = employerRepository.findByEmail(employerEmail);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EmployerDoesNotExistException("Employer does not exist");
        }
        return shiftRepository.findShiftsByEmployer(employer);
    }
    public List<Shift> getShiftsByEmployee(String employeeEmail) {
        Employee employee = employeeRepository.findByEmail(employeeEmail);
        if (employee == null) {
            throw new EmployeeDoesNotExistException("Employee does not exist");
        }
        Employer employer = employee.getEmployer();
        return shiftRepository.findShiftsByEmployer(employer);
    }

    @Transactional
    public void deleteShift(Long id) {
        Shift shift = null;
        Employer employer = null;
        try {
            shift = shiftRepository.findShiftById(id);
            employer = shift.getEmployer();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ShiftDoesNotExistException("Shift does not exist");
        }

        employer.getShifts().remove(shift);
        employerRepository.save(employer);

        shiftRepository.deleteById(id);
    }

    public void updateShift(Shift shift) {
        Shift storedShift = shiftRepository.findShiftById(shift.getId());
        if (storedShift == null) {
            throw new ShiftDoesNotExistException("Shift does not exist");
        }
        if (shift.getDescription() != null) {
            storedShift.setDescription(shift.getDescription());
        }
        if (shift.getStartTime() != null) {
            storedShift.setStartTime(shift.getStartTime());
        }
        if (shift.getEndTime() != null) {
            storedShift.setEndTime(shift.getEndTime());
        }
        shiftRepository.save(storedShift);
    }

    public void acceptShift(Long shiftId, String employeeEmail) {
        Shift shift = null;
        shift = shiftRepository.findShiftById(shiftId);
        if (shift == null) {
            throw new ShiftDoesNotExistException("Shift does not exist");
        }
        Employee employee = employeeRepository.findByEmail(employeeEmail);
        shift.setEmployee(employee);
        shiftRepository.save(shift);
    }

    public List<Shift> getShiftsAcceptedByEmployee(String employeeEmail) {
        Employee employee = employeeRepository.findByEmail(employeeEmail);
        if (employee == null) {
            throw new EmployeeDoesNotExistException("Employee does not exist");
        }
        return shiftRepository.findShiftsByEmployee(employee);
    }
}
