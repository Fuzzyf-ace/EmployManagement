package com.daiming.employmanagement.service;

import com.daiming.employmanagement.model.Employer;
import com.daiming.employmanagement.model.Shift;
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

    @Transactional
    public void addShift(Shift shift, String employerEmail) {
        Employer employer = employerRepository.findByEmail(employerEmail);
        shift.setEmployer(employer);
        shiftRepository.save(shift);
    }

    public List<Shift> getShiftsByEmployer(String employerEmail) {
        Employer employer = employerRepository.findByEmail(employerEmail);
        return shiftRepository.findShiftsByEmployer(employer);
    }

    @Transactional
    public void deleteShift(Long id) {
        Shift shift = shiftRepository.findShiftsById(id);
        Employer employer = shift.getEmployer();

        employer.getShifts().remove(shift);
        employerRepository.save(employer);

        shiftRepository.deleteById(id);
    }

    @Transactional
    public void updateShift(Shift shift) {
        Shift storedShift = shiftRepository.findShiftsById(shift.getId());
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
}
