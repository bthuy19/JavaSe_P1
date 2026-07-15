package org.example.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Student {
    private String studentId;
    private String fullName;
    private LocalDate doB;
    private String phoneNumber;
    private LocalDate enrollmentDate;
    private double discountPercentage = 0;

    protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Student(String studentId, String fullName, LocalDate doB, String phoneNumber, LocalDate enrollmentDate) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.doB = doB;
        this.phoneNumber = phoneNumber;
        this.enrollmentDate = enrollmentDate;
    }

    public Student(String studentId, String fullName, LocalDate doB, LocalDate enrollmentDate) {
    }

    public abstract void   showStudentInfo();

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDoB() {
        return doB;
    }

    public void setDoB(LocalDate doB) {
        this.doB = doB;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
