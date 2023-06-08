package com.daiming.employmanagement.repository;

import com.daiming.employmanagement.model.Employee;
import com.daiming.employmanagement.model.Employer;
import com.daiming.employmanagement.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findShiftsByEmployer(Employer employer);

    List<Shift> findShiftsByEmployee(Employee employee);

    Shift findShiftsById(Long id);

}
