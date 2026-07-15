package org.example.utils;

import org.example.exception.InvalidDateException;
import org.example.exception.InvalidFullNameException;
import org.example.exception.InvalidPhoneNumberException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

// hamf kieemr tra du lieu dau vao, neu sai nem throw ra ca excetion vua tao
public class DataValidator {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static void validateFullName(String fullName) throws InvalidFullNameException {
        if(fullName == null || fullName.length() < 10 || fullName.length() > 50){
            throw new InvalidFullNameException("Tên phải từ 10 đến 50 kí tự " + fullName);
        }
        //validate đủ chữ

        if(fullName.matches(".*\\d.*")){
            throw new InvalidFullNameException("Tên khong được chứa chữ số: " + fullName);
        }
    }
    public static void validatePhoneNumber(String phoneNumber) throws InvalidPhoneNumberException{
        if(phoneNumber == null || !phoneNumber.matches("^(090|098|091|031|035|038)\\d{7}$")){
            throw new InvalidPhoneNumberException("So dien thoai khong hop le: phai 10 so va bat dau boi so quy dinh" + phoneNumber);
        }
    }

    public static LocalDate parseAndValidateDate(String dateStr) throws InvalidDateException {
        try {
            return LocalDate.parse(dateStr, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Sai định dạng ngày (cần dd/MM/yyyy): " + dateStr);
        }
    }
    public static void validateAge(LocalDate doB, LocalDate enrollmentDate) throws InvalidDateException{
        if(ChronoUnit.YEARS.between(doB,enrollmentDate) < 18){
            throw new InvalidDateException("Hoc vien chua du 18 tuoi tai thoi diem hien tai");
        }
    }

    public static void validateShortTermDates(LocalDate enrollmentDate, LocalDate courseEndDate) throws InvalidDateException{
        if(ChronoUnit.MONTHS.between(enrollmentDate, courseEndDate) < 3){
            throw new InvalidDateException("Nhay ket thuc phai sau nay nhap hojc it nhat 3 thang");
        }
    }
    public static void validateLongTermDates(LocalDate enrollmentDate, LocalDate internshipStartDate, LocalDate graduationDate) throws InvalidDateException{
        if(!enrollmentDate.isBefore(internshipStartDate) || !internshipStartDate.isBefore(graduationDate)){
            throw new InvalidDateException("Trình tự thời gian sai: Nhập học < Thực tập < Tốt nghiệp.");
        }
    }
}
