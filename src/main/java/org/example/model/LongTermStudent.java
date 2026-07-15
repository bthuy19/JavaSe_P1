package org.example.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LongTermStudent extends Student{
   private LocalDate internshipStartDate;
   private LocalDate graduationDate;
   private double gpa ;

   protected static final DateTimeFormatter DATE_FOMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public LongTermStudent(String studentId, String fullName, LocalDate doB, String phoneNumber, LocalDate enrollmentDate, LocalDate internshipStartDate, LocalDate graduationDate, double gpa) {
        super(studentId, fullName, doB, phoneNumber, enrollmentDate);
        this.internshipStartDate = internshipStartDate;
        this.graduationDate = graduationDate;
        this.gpa = gpa;
    }

    public LocalDate getInternshipStartDate() {
        return internshipStartDate;
    }

    public void setInternshipStartDate(LocalDate internshipStartDate) {
        this.internshipStartDate = internshipStartDate;
    }

    public LocalDate getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(LocalDate graduationDate) {
        this.graduationDate = graduationDate;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public void showStudentInfo() {
        System.out.printf("[Long-Term]  ID: %-8s | Name: %-20s | DOB: %-10s | Phone: %-12s | Enrolled: %-10s | Intern: %-10s | Grad: %-10s | GPA: %.2f%n",
                getStudentId(), getFullName(),
                getDoB().format(DATE_FORMATTER),
                getPhoneNumber(),
                getEnrollmentDate().format(DATE_FORMATTER),
                internshipStartDate.format(DATE_FORMATTER),
                graduationDate.format(DATE_FORMATTER),
                gpa);

    }
}
