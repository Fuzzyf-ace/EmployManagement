package com.daiming.employmanagement.service;

import com.daiming.employmanagement.exception.EmployeeDoesNotExistException;
import com.daiming.employmanagement.exception.ShiftDoesNotExistException;
import com.daiming.employmanagement.exception.WorkRecordDoesNotExist;
import com.daiming.employmanagement.model.Employee;
import com.daiming.employmanagement.model.Employer;
import com.daiming.employmanagement.model.Shift;
import com.daiming.employmanagement.model.WorkRecord;
import com.daiming.employmanagement.repository.EmployeeRepository;
import com.daiming.employmanagement.repository.ShiftRepository;
import com.daiming.employmanagement.repository.WorkRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class WorkRecordService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ShiftRepository shiftRepository;

    @Autowired
    WorkRecordRepository workRecordRepository;

    /**
     * create a new workRecord when start work
     * @param employeeEmail
     * @param shiftId
     */
    public void startWork(String employeeEmail, Long shiftId) {
        Employee employee = null;
        Shift shift = null;
        WorkRecord workRecord = null;
        employee = employeeRepository.findByEmail(employeeEmail);
        shift = shiftRepository.findShiftById(shiftId);
        if (employee == null) {
            throw new EmployeeDoesNotExistException("Employee does not exist");
        }
        if (shift == null) {
            throw new ShiftDoesNotExistException("Shift does not exist");
        }
        Instant startTime = Instant.now();
        workRecord = new WorkRecord();
        workRecord.setEmployee(employee);
        workRecord.setShift(shift);
        workRecord.setStartTime(startTime);

        workRecordRepository.save(workRecord);
    }

    /**
     * update the workRecord when end work
     * @param workRecordId
     */
    public void endWork(Long workRecordId) {
        WorkRecord workRecord = null;
        workRecord = workRecordRepository.findWorkRecordById(workRecordId);
        try {
            workRecord.setEndTime(Instant.now());
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new WorkRecordDoesNotExist("Work record does not exist");
        }
        workRecordRepository.save(workRecord);
    }

    public List<WorkRecord> getMyWorkRecordsByEmployee(String employeeEmail) {
        Employee employee = employeeRepository.findByEmail(employeeEmail);
        return workRecordRepository.findWorkRecordsByEmployee(employee);
    }
}
