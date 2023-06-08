package com.daiming.employmanagement.service;

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
    @Transactional
    public void startWork(String employeeEmail, Long shiftId) {
        Employee employee = employeeRepository.findByEmail(employeeEmail);
        Shift shift = shiftRepository.findShiftById(shiftId);
        Instant startTime = Instant.now();
        System.out.println("startTime: " + startTime);
        WorkRecord workRecord = new WorkRecord();
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
        WorkRecord workRecord = workRecordRepository.findWorkRecordById(workRecordId);
        System.out.print(Instant.now());
        workRecord.setEndTime(Instant.now());
        workRecordRepository.save(workRecord);
    }

    public List<WorkRecord> getMyWorkRecordsByEmployee(String employeeEmail) {
        Employee employee = employeeRepository.findByEmail(employeeEmail);
        return workRecordRepository.findWorkRecordsByEmployee(employee);
    }
}
