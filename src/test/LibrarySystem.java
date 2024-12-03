package test;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;

public class LibrarySystem {
	
	//데이터베이스 연결
	public static Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";  // MySQL 사용자명
        String password = "root";  // MySQL 비밀번호
        return DriverManager.getConnection(url, user, password);
    }
	//관리자확인
	//sql쿼리: ALTER TABLE Customers ADD COLUMN role VARCHAR(10) DEFAULT 'user';
	public static boolean isAdmin(int userId) {
	    String query = "SELECT role FROM Customers WHERE id = ?";
	    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            String role = rs.getString("role");
	            return "admin".equalsIgnoreCase(role); //관리자 여부 확인
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; //기본값: 관리자 아님
	}
	//도서 추가(addBook)
	public static void addBook(int id, String title, String author, String type, String publisher, String pubDate, double rate, int stock, String locationId) {
		if (!isAdmin(id)) {
			System.out.println("권한이 없습니다");
			return;
		}
		
		String query = "INSERT INTO Books (id, title, author, type, publisher, pub_date, rate, stock, location_⁯id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, id);
			stmt.setString(2, title);
            stmt.setString(3, author);
            stmt.setString(4, type);
            stmt.setString(5, publisher);
            stmt.setString(6, pubDate);
            stmt.setDouble(7, rate);
            stmt.setInt(8, stock);
            stmt.setString(9, locationId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("새로운 도서 추가");
            } else {
                System.out.println("도서 추가 실패");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}
	//회원 추가(addCustomer)
	public static void addCustomer(String name, String email, String phoneNumber, String birthDate, String name_id, String password) {
        String query = "INSERT INTO Customers (name, email, phone_number, birth_date, name_id, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phoneNumber);
            stmt.setString(4, birthDate);
            stmt.setString(5, name_id);
            stmt.setString(6, password);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("새로운 회원이 추가되었습니다");
            } else {
                System.out.println("회원 추가 실패");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	
	//도서 대여(rentBook)
	//도서검색 (도서명/저자명/출판사로 검색)
	public static void searchBooks(String searchBook) {
        String query = "SELECT * FROM Books WHERE title LIKE ? OR author LIKE ? OR publisher LIKE ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            String searchPattern = "%" + searchBook + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("검색된 결과가 없습니다.");
            }
            //도서목록 보여주기
            System.out.println("검색된 도서: ");
            while (rs.next()) {
                System.out.println("번호: " + rs.getString("id") +
                		", 제목: " + rs.getString("title") +
                        ", 저자: " + rs.getString("author") +
                        ", 장르: " + rs.getString("type") +
                        ", 출판사: " + rs.getString("publisher") +
                        ", 출간일: " + rs.getString("pub_date") +
                        ", rate: " + rs.getString("rate") +
                        ", 재고: " + rs.getInt("stock") +
                        ", 도서위치: " + rs.getInt("Locations_⁯id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }	
	
    //재고에 따라 도서 대여/예약 하기
    public static void rentOrReserveBook(int bookId) {
        String checkQuery = "SELECT stock FROM Books WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(checkQuery)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt("stock");

                if (stock > 0) {
                    //도서대여가능 (재고 있음)
                    String updateStockQuery = "UPDATE Books SET stock = stock - 1 WHERE id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateStockQuery)) {
                        updateStmt.setInt(1, bookId);
                        updateStmt.executeUpdate();
                        System.out.println("도서가 대여되었습니다");
                    }
                } else {
                    //도서대여불가 (재고 없음)
                    System.out.println("재고가 없습니다. 예약하시겠습니까?");
                    Scanner scanner = new Scanner(System.in);
                    String response = scanner.nextLine();

                    if (response.equalsIgnoreCase("yes")) {
                        String insertReservationQuery = "INSERT INTO Reservations (book_id) VALUES (?)";
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertReservationQuery)) {
                            insertStmt.setInt(1, bookId);
                            insertStmt.executeUpdate();
                            System.out.println("예약이 완료되었습니다");
                        }
                    } else {
                        System.out.println("예약이 취소되었습니다");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }	
    
    
	//도서 반납(returnBook)
    public static void returnBook(int bookId) {
        String updateStockQuery = "UPDATE Books SET stock = stock + 1 WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(updateStockQuery)) {
            stmt.setInt(1, bookId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("도서가 반납되었습니다.");
            } else {
                System.out.println("반납할 도서를 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    
    //프로그램 메뉴 (mainMenu)
    private static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("도서대출 프로그램");
            System.out.println("1. 회원가입");
            System.out.println("2. 도서대출");
            System.out.println("3. 도서반납");
            System.out.println("4. 관리자메뉴");
            System.out.println("5. 종료");

            System.out.print("\n원하는 메뉴를 선택하세요: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:	//회원가입
                    System.out.println("\n회원정보를 입력해주세요");
                    System.out.print("이름: ");
                    String name = scanner.nextLine();
                    System.out.print("이메일주소: ");
                    String email = scanner.nextLine();
                    System.out.print("핸드폰번호: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("생년월일 (YYYY-MM-DD): ");
                    String birthDate = scanner.nextLine();
                    System.out.print("아이디: ");
                    String name_id = scanner.nextLine();
                    System.out.print("비밀번호: ");
                    String password = scanner.nextLine();

                    // 회원추가
                    addCustomer(name, email, phoneNumber, birthDate, name_id, password);
                    break;
                    
                case 2:	//도서대출
                	System.out.print("찾으려는 책의 이름/저자/출판사 를 입력하세요: ");
                    String searchBook = scanner.nextLine();
                    searchBooks(searchBook);
                    System.out.println("대여하는 책의 번호를 입력하세요:");
                    int bookId = scanner.nextInt();
                	rentOrReserveBook(bookId);
                    break;
                case 3:	//반납
                    System.out.print("반납할 도서의 ID를 입력하세요: ");
                   	int returnBookId = scanner.nextInt();
                   	returnBook(returnBookId);
                    break;
                case 4:	//관리자메뉴
                    //관리자만 도서 추가할 수 있음
                    System.out.print("ID를 입력하세요: ");
                    int adminId = scanner.nextInt();
                    scanner.nextLine(); // 개행 처리

                    if (!isAdmin(adminId)) {
                        System.out.println("권한이 없습니다. 관리자만 접근 가능합니다.");
                        break;
                    }

                    System.out.println("도서 정보를 입력하세요");
                    System.out.print("제목: ");
                    String title = scanner.nextLine();
                    System.out.print("저자: ");
                    String author = scanner.nextLine();
                    System.out.print("장르: ");
                    String type = scanner.nextLine();
                    System.out.print("출판사: ");
                    String publisher = scanner.nextLine();
                    System.out.print("출간일 (YYYY-MM-DD): ");
                    String pubDate = scanner.nextLine();
                    System.out.print("평점: ");
                    double rate = scanner.nextDouble();
                    System.out.print("재고: ");
                    int stock = scanner.nextInt();
                    System.out.print("위치 ID: ");
                    String locationId = scanner.nextLine();

                    addBook(adminId, title, author, type, publisher, pubDate, rate, stock, locationId);
                    break;
                case 5:	//종료
                    System.out.println("끝");
                    scanner.close();
                    return;
                default:
                    System.out.println("다시해보세요");
            }
        }
    }
    
    
	public static void main(String[] args) {
		mainMenu();

	}

}
