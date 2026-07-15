package org.example.model;

import java.time.LocalDate;

public class ShortTermStudent extends Student{
    private LocalDate courseEndDate;
    private double finalProjectScore;


    public ShortTermStudent(String studentId, String fullName, LocalDate doB, String phoneNumber, LocalDate enrollmentDate, LocalDate courseEndDate, double finalProjectScore) {
        super(studentId, fullName, doB, phoneNumber, enrollmentDate);
        this.courseEndDate = courseEndDate;
        this.finalProjectScore = finalProjectScore;
    }
    public ShortTermStudent(String studentId, String fullName, LocalDate doB, LocalDate enrollmentDate, LocalDate courseEndDate, double finalProjectScore) {
        super(studentId, fullName, doB, enrollmentDate);
        this.courseEndDate = courseEndDate;
        this.finalProjectScore = finalProjectScore;
    }



    public LocalDate getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(LocalDate courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public double getFinalProjectScore() {
        return finalProjectScore;
    }

    public void setFinalProjectScore(double finalProjectScore) {
        this.finalProjectScore = finalProjectScore;
    }

    @Override
    public void showStudentInfo() {
        System.out.printf("[Short-Term] ID: %-8s | Name: %-20s | DOB: %-10s | Phone: %-12s | Enrolled: %-10s | End Date: %-10s | Score: %.2f%n",
                getStudentId(), getFullName(),
                getDoB().format(DATE_FORMATTER),
                getPhoneNumber(),
                getEnrollmentDate().format(DATE_FORMATTER),
                courseEndDate.format(DATE_FORMATTER),
                finalProjectScore);

    }
}
