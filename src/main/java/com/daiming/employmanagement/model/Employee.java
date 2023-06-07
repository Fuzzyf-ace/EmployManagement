package com.daiming.employmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 45)
    private String email;

    @Lob
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", length = 45)
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name", length = 45)
    @JsonProperty("last_name")
    private String lastName;

    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "pay_rate", precision = 10)
    @JsonProperty("pay_rate")
    private BigDecimal payRate;

    @Column(name = "user_role", length = 45)
    @JsonProperty("user_role")
    private UserRole userRole = UserRole.EMPLOYEE;

    @JoinColumn(name = "employer")
    @JsonIgnore
    @ManyToOne
    private Employer employer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BigDecimal getPayRate() {
        return payRate;
    }

    public void setPayRate(BigDecimal payRate) {
        this.payRate = payRate;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

}