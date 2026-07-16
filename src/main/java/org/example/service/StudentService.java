package org.example.service;

import org.example.dao.StudentDAO;
import org.example.exception.InvalidDateException;
import org.example.exception.InvalidFullNameException;
import org.example.exception.InvalidPhoneNumberException;
import org.example.model.LongTermStudent;
import org.example.model.ShortTermStudent;
import org.example.model.Student;
import org.example.utils.DataValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// docj file kiem tra ngoai le tung dong
public class StudentService {
    private StudentDAO studentDAO = new StudentDAO();
    private List<Student> studentList = new ArrayList<>();

    public void processShortTermFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] data = line.split(",");
                    if (data.length < 7) continue;
                    String id = data[0].trim();
                    String name = data[1].trim();
                    DataValidator.validateFullName(name);

                    LocalDate doB = DataValidator.parseAndValidateDate(data[2].trim());
                    String phone = data[3].trim();
                    DataValidator.validatePhoneNumber(phone);

                    LocalDate enroll = DataValidator.parseAndValidateDate(data[4].trim());
                    DataValidator.validateAge(doB, enroll);

                    LocalDate end = DataValidator.parseAndValidateDate(data[5].trim());
                    DataValidator.validateShortTermDates(enroll, end);

                    double score = Double.parseDouble(data[6].trim());

                    ShortTermStudent student = new ShortTermStudent(id, name, doB, phone, enroll, end, score);
                    studentDAO.insertStudent(student);
                    studentList.add(student);
                    student.showStudentInfo();


                } catch (InvalidFullNameException | InvalidPhoneNumberException | InvalidDateException e) {
                    System.err.println("[LỖI DỮ LIỆU DÒNG] " + line + " -> " + e.getMessage());

                } catch (Exception e) {
                    System.err.println("Input files have unknown errors !!!");
                }
            }
        } catch (Exception e) {
            System.err.println("Input files have unknown errors !!!");
        }
    }

    public void processLongTermFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] data = line.split(",");
                    if (data.length < 8) continue;

                    String id = data[0].trim();
                    String name = data[1].trim();
                    DataValidator.validateFullName(name);

                    LocalDate dob = DataValidator.parseAndValidateDate(data[2].trim());

                    String phone = data[3].trim();
                    DataValidator.validatePhoneNumber(phone);

                    LocalDate enroll = DataValidator.parseAndValidateDate(data[4].trim());
                    DataValidator.validateAge(dob, enroll);

                    LocalDate intern = DataValidator.parseAndValidateDate(data[5].trim());
                    LocalDate grad = DataValidator.parseAndValidateDate(data[6].trim());
                    DataValidator.validateLongTermDates(enroll, intern, grad);

                    double gpa = Double.parseDouble(data[7].trim());

                    LongTermStudent student = new LongTermStudent(id, name, dob, phone, enroll, intern, grad, gpa);
                    studentDAO.insertStudent(student);
                    studentList.add(student);

                    // In thông tin đọc thành công
                    student.showStudentInfo();

                } catch (InvalidFullNameException | InvalidPhoneNumberException | InvalidDateException e) {
                    System.err.println("[LỖI DỮ LIỆU DÒNG] " + line + " -> " + e.getMessage());

                } catch (Exception e) {
                    System.err.println("Input files have unknown errors !!!");
                }
            }
        } catch (Exception e) {
            System.err.println("Input files have unknown errors !!!");
        }
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void grantScholarship(int n) {
        System.out.println("\n--- DANH SÁCH " + n + " HỌC VIÊN NHẬN HỌC BỔNG ---");
        //1. Locj vaf sap xep hojc vien dai han
        List<LongTermStudent> longTermStudents = studentList.stream()
                .filter(s -> s instanceof LongTermStudent)
                .map(s -> (LongTermStudent) s)
                .sorted(Comparator.comparing(LongTermStudent::getGpa).reversed()
                        .thenComparing(Student::getFullName))
                .toList();
        List<ShortTermStudent> shortTermStudents = studentList.stream()
                .filter(s -> s instanceof ShortTermStudent)
                .map(s -> (ShortTermStudent) s)
                .sorted(Comparator.comparing(ShortTermStudent::getFinalProjectScore).reversed()
                        .thenComparing((s1, s2) -> {
                            // Tính khoảng thời gian khóa học
                            long duration1 = ChronoUnit.DAYS.between(s1.getEnrollmentDate(), s1.getCourseEndDate());
                            long duration2 = ChronoUnit.DAYS.between(s2.getEnrollmentDate(), s2.getCourseEndDate());
                            return Long.compare(duration1, duration2); // Sắp xếp tăng dần theo độ dài khóa học

                        })).toList();
        List<Student> scholarshipList = new ArrayList<>(longTermStudents);
        scholarshipList.addAll(shortTermStudents);

        scholarshipList.stream()
                .limit(n)
                .forEach(Student::showStudentInfo);


    }

    public void displaySortedStudents() {
        System.out.println("\n--- DANH SÁCH TOÀN BỘ HỌC VIÊN TẠI TRUNG TÂM ---");
        studentList.stream()
                .sorted(Comparator.comparing(Student::getFullName, Comparator.reverseOrder())
                        .thenComparing(Student::getEnrollmentDate))
                .forEach(Student::showStudentInfo);
    }

    /*
    // Yêu cầu ưu đãi (cũ)
    public void processDiscountFile(String filePath) {
        System.out.println("\n--- ĐỌC FILE ƯU ĐÃI ---");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] data = line.split(",");
                    if (data.length < 2) continue;
                    String id = data[0].trim();
                    double discount = Double.parseDouble(data[1].trim());

                    boolean updated = studentDAO.updateDiscount(id, discount);
                    if (!updated) {
                        System.out.println("Cảnh báo: Không tìm thấy học viên có mã " + id + " để áp dụng ưu đãi");
                    } else {
                        System.out.println("Đã cập nhật ưu đãi " + discount + "% cho học viên " + id);
                        for (Student s : studentList) {
                            if (s.getStudentId().equals(id)) {
                                s.setDiscountPercentage(discount);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Lỗi khi xử lý dòng ưu đãi: " + line);
                }
            }
        } catch (Exception e) {
            System.err.println("Không thể đọc file ưu đãi !!!");
        }
    }
    */

    // Thay đổi như Black Friday
    public void processBlackFridayFile(String filePath) {
        System.out.println("\n--- ĐỌC FILE ƯU ĐÃI (BLACK FRIDAY LOGIC) ---");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if (line != null && !line.trim().isEmpty()) {
                String[] newDiscounts = line.split(",");
                
                // Giả định áp dụng ưu đãi theo thứ tự danh sách học viên hiện tại
                for (int i = 0; i < Math.min(studentList.size(), newDiscounts.length); i++) {
                    Student currentStudent = studentList.get(i);
                    double newDiscount = Double.parseDouble(newDiscounts[i].trim());
                    
                    // Điều kiện: Ưu đãi mới lớn hơn ưu đãi hiện tại (Tương đương Giá mới < Giá cũ)
                    if (newDiscount > currentStudent.getDiscountPercentage()) {
                        studentDAO.updateBlackFridayDiscount(currentStudent.getStudentId(), newDiscount);
                        
                        currentStudent.setDiscountPercentage(newDiscount);
                        currentStudent.setIsDiscounted(1);
                        System.out.println("Đã update ưu đãi Black Friday (" + newDiscount + "%) cho HV: " + currentStudent.getStudentId());
                    } else {
                        System.out.println("Ưu đãi mới nhỏ hơn hoặc bằng hiện tại. Bỏ qua HV: " + currentStudent.getStudentId());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi đọc file ưu đãi Black Friday!");
        }
    }
}

