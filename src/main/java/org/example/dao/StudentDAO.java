package org.example.dao;

import org.example.model.LongTermStudent;
import org.example.model.ShortTermStudent;
import org.example.model.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/techmaster";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public void insertStudent(Student student) throws SQLException{
        String sql = "INSERT INTO students (student_id, full_name, date_of_birth, phone_number, enrollment_date, " +
                "student_type, course_end_date, final_project_score, internship_start_date, graduation_date, gpa, discount_percentage) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection conn = DriverManager.getConnection(URL,USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getFullName());
            pstmt.setObject(3,student.getDoB());
            pstmt.setString(4,student.getPhoneNumber());
            pstmt.setObject(5,student.getEnrollmentDate());

            if( student instanceof ShortTermStudent){
                ShortTermStudent sts = (ShortTermStudent) student;
                pstmt.setString(6, "SHORT_TERM");
                pstmt.setObject(7, sts.getCourseEndDate() );
                pstmt.setDouble(8, sts.getFinalProjectScore());
                pstmt.setObject(9, null);
                pstmt.setObject(10, null);
                pstmt.setObject(11, null);
            } else if (student instanceof LongTermStudent) {
                LongTermStudent lts = (LongTermStudent) student;
                pstmt.setString(6, "LONG_TERM");
                pstmt.setObject(7, null);
                pstmt.setObject(8, null);
                pstmt.setObject(9, lts.getInternshipStartDate());
                pstmt.setObject(10, lts.getGraduationDate());
                pstmt.setDouble(11, lts.getGpa());
            }
            pstmt.setDouble(12, student.getDiscountPercentage());
            pstmt.executeUpdate();

        }

    }

    public boolean updateDiscount(String studentId, double discountPercentage) throws SQLException {
        String sql = "UPDATE students SET discount_percentage = ? WHERE student_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, discountPercentage);
            pstmt.setString(2, studentId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
