package org.example;

import org.example.service.StudentService;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    // =========================================================================
    // Yêu cầu 6: Kiến thức Exception
    // Phân biệt Checked Exception và Unchecked Exception:
    // - Checked Exception: Là các ngoại lệ bị kiểm tra bắt buộc tại thời điểm 
    //   biên dịch (compile-time). Lập trình viên bắt buộc phải xử lý bằng 
    //   khối lệnh try-catch hoặc ném tiếp ra ngoài bằng từ khóa throws.
    //   (Các lớp kế thừa từ Exception nhưng không kế thừa RuntimeException).
    // - Unchecked Exception: Là các ngoại lệ xảy ra tại thời điểm chạy (runtime), 
    //   thường xuất phát từ lỗi logic. Trình biên dịch không bắt lỗi này,
    //   và lập trình viên không bị ép buộc phải try-catch hay throws.
    //   (Các lớp kế thừa từ RuntimeException hoặc Error).
    //
    // 2 loại exception đại diện trong 2 nhóm đã sử dụng trong code này:
    // 1. Checked Exception: 
    //    - InvalidDateException (Exception do tự định nghĩa extends Exception)
    //    - IOException (Xảy ra trong BufferedReader khi đọc file)
    //    - SQLException (Xảy ra ở StudentDAO khi kết nối/tương tác Database)
    // 2. Unchecked Exception: 
    //    - NumberFormatException (Xảy ra khi parseInt hoặc parseDouble bị lỗi định dạng)
    //    - NullPointerException (Nếu dữ liệu bị null trong quá trình thao tác)
    // =========================================================================
    public static void main(String[] args) {

                StudentService service = new StudentService();

                System.out.println("=== ĐỌC DỮ LIỆU TỪ FILE & LƯU DATABASE ===");
                // Giả sử 2 file CSV được đặt ở thư mục gốc của project
                System.out.println("\n[1] Đọc danh sách học viên ngắn hạn:");
                service.processShortTermFile("shortTermStudent.csv");

                System.out.println("\n[2] Đọc danh sách học viên dài hạn:");
                service.processLongTermFile("longTermStudent.csv");

                // --- Chạy Yêu cầu 4.2 ---
                service.displaySortedStudents();

                // --- Chạy Yêu cầu 5: Mở rộng DB & Cập nhật file ưu đãi ---
                // service.processDiscountFile("uudai.txt"); // Đã comment theo yêu cầu cũ
                service.processBlackFridayFile("uudai.txt"); // Logic Black Friday mới

                // --- Chạy Yêu cầu 4.1 ---
                try (Scanner scanner = new Scanner(System.in)) {
                    int n = 0;
                    while (true) {
                        System.out.print("\nNhập số lượng học viên xét học bổng (N từ 3 đến 10): ");
                        try {
                            n = Integer.parseInt(scanner.nextLine());
                            if (n >= 3 && n <= 10) {
                                break;
                            } else {
                                System.out.println("Vui lòng nhập N trong khoảng [3, 10]!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Lỗi: Phải nhập số nguyên hợp lệ!");


                        }
                    }

                    service.grantScholarship(n);
                }
            }

        }


