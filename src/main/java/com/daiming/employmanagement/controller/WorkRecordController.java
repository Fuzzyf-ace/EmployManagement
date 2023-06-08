package com.daiming.employmanagement.controller;

import com.daiming.employmanagement.model.Shift;
import com.daiming.employmanagement.model.WorkRecord;
import com.daiming.employmanagement.service.WorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class WorkRecordController {

    @Autowired
    WorkRecordService workRecordService;

    @PostMapping("/employee/startWork")
    public void startWork(Principal principal, @RequestBody Shift shift) {
        workRecordService.startWork(principal.getName(), shift.getId());
    }

    @PostMapping("/employee/endWork")
    public void endWork(@RequestBody WorkRecord workRecord) {
        workRecordService.endWork(workRecord.getId());
    }

    @GetMapping("/employee/getMyWorkRecords")
    public List<WorkRecord> getMyWorkRecords(Principal principal) {
        return workRecordService.getMyWorkRecordsByEmployee(principal.getName());
    }
}
