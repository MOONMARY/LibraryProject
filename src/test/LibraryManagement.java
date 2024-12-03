package test;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;

public class LibraryManagement {

    // 데이터베이스 연결
    private static Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";  // MySQL 사용자명
        String password = "your_password";  // MySQL 비밀번호
        return DriverManager.getConnection(url, user, password);
    }

    // 1. 도서 목록 출력
    private static void showBooks() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM books WHERE stock > 0";  // 재고가 있는 도서만 조회
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Available Books:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("book_id") +
                                   ", Title: " + rs.getString("title") +
                                   ", Author: " + rs.getString("author") +
                                   ", ISBN: " + rs.getString("isbn") +
                                   ", Stock: " + rs.getInt("stock"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. 고객 목록 출력
    private static void showCustomers() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM customers";
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Customers:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("customer_id") +
                                   ", Name: " + rs.getString("name") +
                                   ", Email: " + rs.getString("email") +
                                   ", Phone: " + rs.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. 도서 대여
    private static void rentBook(int customerId, int bookId) {
        try (Connection conn = connect()) {
            // 도서 재고 확인
            String checkStockQuery = "SELECT stock FROM books WHERE book_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(checkStockQuery)) {
                stmt.setInt(1, bookId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt("stock") > 0) {
                    // 재고가 있으면 대여 진행
                    String rentalDate = new java.sql.Date(new Date().getTime()).toString();
                    String rentQuery = "INSERT INTO rentals (customer_id, book_id, rental_date) VALUES (?, ?, ?)";
                    try (PreparedStatement rentStmt = conn.prepareStatement(rentQuery)) {
                        rentStmt.setInt(1, customerId);
                        rentStmt.setInt(2, bookId);
                        rentStmt.setString(3, rentalDate);
                        rentStmt.executeUpdate();
                    }

                    // 도서 재고 차감
                    String updateStockQuery = "UPDATE books SET stock = stock - 1 WHERE book_id = ?";
                    try (PreparedStatement updateStockStmt = conn.prepareStatement(updateStockQuery)) {
                        updateStockStmt.setInt(1, bookId);
                        updateStockStmt.executeUpdate();
                    }

                    System.out.println("Book rented successfully!");
                } else {
                    System.out.println("Sorry, this book is out of stock.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. 도서 반납
    private static void returnBook(int rentalId) {
        try (Connection conn = connect()) {
            // 반납일 업데이트
            String returnQuery = "UPDATE rentals SET return_date = ? WHERE rental_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(returnQuery)) {
                String returnDate = new java.sql.Date(new Date().getTime()).toString();
                stmt.setString(1, returnDate);
                stmt.setInt(2, rentalId);
                stmt.executeUpdate();
            }

            // 재고 업데이트
            String getBookQuery = "SELECT book_id FROM rentals WHERE rental_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(getBookQuery)) {
                stmt.setInt(1, rentalId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int bookId = rs.getInt("book_id");

                    // 재고 증가
                    String updateStockQuery = "UPDATE books SET stock = stock + 1 WHERE book_id = ?";
                    try (PreparedStatement updateStockStmt = conn.prepareStatement(updateStockQuery)) {
                        updateStockStmt.setInt(1, bookId);
                        updateStockStmt.executeUpdate();
                    }
                }
            }

            System.out.println("Book returned successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 프로그램 메뉴
    private static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Show Available Books");
            System.out.println("2. Show Customers");
            System.out.println("3. Rent a Book");
            System.out.println("4. Return a Book");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showBooks();
                    break;
                case 2:
                    showCustomers();
                    break;
                case 3:
                    System.out.print("Enter customer ID: ");
                    int customerId = scanner.nextInt();
                    System.out.print("Enter book ID: ");
                    int bookId = scanner.nextInt();
                    rentBook(customerId, bookId);
                    break;
                case 4:
                    System.out.print("Enter rental ID: ");
                    int rentalId = scanner.nextInt();
                    returnBook(rentalId);
                    break;
                case 5:
                    System.out.println("Exiting the system...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        mainMenu();
    }
}
