package project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ManagerDaoImpl implements ManagerDao {
	static final String dburl = "jdbc:mysql://localhost:3306/library_db";
	static final String dbuser = "library_manager";
	static final String dbpass = "0000";

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dburl, dbuser, dbpass);
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드 실패!" + e.getMessage());
		}
		return conn;
	}

	// 관리자 로그인

	// 전체 도서 목록
	@Override
	public List<ManagerVo> getList() {
		List<ManagerVo> list = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			stmt = conn.createStatement();

			String sql = "SELECT Books.id, Books.title, Authors.author_name, Authors.author_email,  "
					+ "	Types.type_name, Publishers.publisher_name, Publishers.publisher_number, Publishers.publisher_email, Books.pub_date, "
					+ "	Books.rate, Books.stock, Books.Locations_id, "
					+ " Rental.rental_date, Rental.return_expect, Customers.name, Customers.name_id " + "FROM Books "
					+ "JOIN Types ON Books.type_id = Types.type_id  "
					+ "JOIN Authors ON Books.author_id = Authors.author_id "
					+ "JOIN Publishers ON Books.publisher_id = Publishers.publisher_id "
					+ "LEFT JOIN Rental ON Books.id = Rental.book_id "
					+ "LEFT JOIN Customers ON Rental.customer_id = Customers.id " + "ORDER BY Books.id;";
			rs = stmt.executeQuery(sql);

			// 각 레코드를 List<UserVo>로 변환
			while (rs.next()) {
				int bookId = rs.getInt("id");
				String title = rs.getString("title");
				String authorName = rs.getString("author_name");
				String authorEmail = rs.getString("author_email");
				String typeName = rs.getString("type_name");
				String publisherName = rs.getString("publisher_name");
				String publisherNumber = rs.getString("publisher_number");
				String publisherEmail = rs.getString("publisher_email");
				String pubDate = rs.getString("pub_date");
				int rate = rs.getInt("rate");
				int stock = rs.getInt("stock");
				int locationsId = rs.getInt("Locations_id");
				String rentalDate = rs.getString("rental_date");
				if (rentalDate == null)
					rentalDate = "null";
				String returnExpect = rs.getString("return_expect");
				if (returnExpect == null)
					returnExpect = "null";
				String name = rs.getString("name");
				if (name == null)
					name = "null";
				String nameId = rs.getString("name_id");
				if (nameId == null)
					nameId = "null";

				ManagerVo vo = new ManagerVo(bookId, title, authorName, authorEmail, typeName, publisherName,
						publisherNumber, publisherEmail, pubDate, rate, stock, locationsId, rentalDate, returnExpect,
						name, nameId);

				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 신규 도서 추가
	@Override
	public boolean insert(ManagerVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int insertedCount = 0;

		try {
			conn = getConnection();

			// Locations_id 중복 여부 확인
			String sql4 = "SELECT COUNT(*) FROM Books WHERE Locations_id = ?";
			pstmt = conn.prepareStatement(sql4);
			pstmt.setInt(1, vo.getLocationsId());
			rs = pstmt.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				System.out.println("ERROR: 중복된 Locations_id입니다: " + vo.getLocationsId());
				return false; // 중복된 경우 삽입 중단
			}
			rs.close();
			pstmt.close();

			String sql3 = "SELECT author_id FROM authors WHERE author_name LIKE ?";
			pstmt = conn.prepareStatement(sql3);
			pstmt.setString(1, vo.getAuthorName());
			rs = pstmt.executeQuery();

			int author_id = 0;
			if (rs.next()) { // 데이터가 있는 경우
				author_id = rs.getInt(1);
			}
			rs.close();
			pstmt.close();

			String sql2 = "SELECT publisher_id FROM publishers WHERE publisher_name LIKE ?";
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, vo.getPublisherName());
			rs = pstmt.executeQuery();

			int publisher_id = 0;
			if (rs.next()) { // 데이터가 있는 경우
				publisher_id = rs.getInt(1);
			}
			rs.close();
			pstmt.close();

			String sql = "INSERT INTO Books (title, pub_date, rate, stock, Locations_id, type_id, publisher_id, author_id) "
					+ "VALUES (?, ?, ?, 1, ?, ?, ?, ?) ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getPubDate());
			pstmt.setInt(3, vo.getRate());
			pstmt.setInt(4, vo.getLocationsId());
			pstmt.setInt(5, vo.getTypeId());
			pstmt.setInt(6, publisher_id);
			pstmt.setInt(7, author_id);

			insertedCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 1 == insertedCount;
	}

	// 도서 삭제
	public boolean deleteBook(ManagerVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean success = false;

		try {
			// Books 테이블 삭제 (나머지 테이블 자동 삭제)
			conn = getConnection();
			String Sql = "DELETE FROM Books WHERE id = ?";
			pstmt = conn.prepareStatement(Sql);
			pstmt.setInt(1, vo.getBookId());
			pstmt.executeUpdate();

			success = true;

		} catch (SQLException e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return success;
	}

//	//회원정보 수정 - 삭제해도 됨!
//	public boolean UpdateCustomer(ManagerVo vo) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		boolean success = false;
//		
//		try {
//			conn = getConnection();
//			//회원 정보 출력
//			String customerSQL = "SELECT * FROM CUSTOMERS WHERE id = ?;";
//			//회원 정보 변경
//			String updateName = "UPDATE CUSTOMERS SET name = '?' WHERE id = ?;";
//			pstmt = conn.prepareStatement(updateName);
//	        pstmt.setString(1, vo.getName());
//	        pstmt.setInt(2, vo.getId());
//	        
//			String updateEmail = "UPDATE CUSTOMERS SET email = '?' WHERE id = ?;";
//			String updatePhoneNum = "UPDATE CUSTOMERS SET phone_number = '?' WHERE id = ?;";
//			String updateBirth = "UPDATE CUSTOMERS SET birth_date = '?' WHERE id = ?;";
//			String updateNameId = "UPDATE CUSTOMERS SET name_id = '?' WHERE id = ?;";
//			String updatePassword = "UPDATE CUSTOMERS SET password = '?' WHERE id = ?;";
//			
//		} catch() {
//			
//		}
//		
//		
//	}

	// 전체 회원 목록
	@Override
	public List<ManagerVo> getCustomerList() {
		List<ManagerVo> list = new ArrayList<>();

		try {
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();

			String sql = "select * from customers";
			ResultSet rs = stmt.executeQuery(sql);

			// 각 레코드를 List<UserVo>로 변환
			while (rs.next()) {
				int customerId = rs.getInt("id");
				String customerName = rs.getString("name");
				String email = rs.getString("email");
				String phoneNumber = rs.getString("phone_number");
				String birthDate = rs.getString("birth_date");
				String customerNameId = rs.getString("name_id");
				String customerPassword = rs.getString("password");

				ManagerVo vo = new ManagerVo(customerId, customerName, email, phoneNumber, birthDate, customerNameId,
						customerPassword);

				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 회원 삭제
	public boolean deleteCustomer(ManagerVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean success = false;

		try {
			// 회원정보 삭제
			conn = getConnection();
			String Sql = "DELETE FROM Customers WHERE id = ?";
			pstmt = conn.prepareStatement(Sql);
			pstmt.setInt(1, vo.getBookId());
			pstmt.executeUpdate();

			success = true;

		} catch (SQLException e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return success;
	}

	// 도서 존재 유무 확인
	public boolean isBookExists(int id) {

		String sql = "SELECT COUNT(*) FROM books WHERE id = ?";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id); // 도서 ID를 쿼리에 설정
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0; // 도서가 존재하면 true 반환
			}

		} catch (SQLException e) {
			e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
		}
		return false; // 도서가 존재하지 않으면 false 반환
	}

	// 회원 존재 유무 확인
	public boolean isCustomerExists(int id) {

		String sql = "SELECT COUNT(*) FROM customers WHERE id = ?";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 신규 작가 있는 경우 추가
	@Override
	public boolean insertAuthor(ManagerVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int insertedCount = 0;

		try {
			conn = getConnection();

			// 작가 존재 여부 확인
			String sql = "SELECT author_name FROM Authors WHERE author_name = ? AND author_email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getAuthorName().trim());
			pstmt.setString(2, vo.getAuthorEmail().trim());
			rs = pstmt.executeQuery();

			if (!rs.next()) { // 작가가 없을 경우 INSERT
				rs.close();
				pstmt.close();

				String sql2 = "INSERT INTO Authors (author_name, author_email) VALUES (?, ?)";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, vo.getAuthorName().trim());
				pstmt.setString(2, vo.getAuthorEmail().trim());

				insertedCount = pstmt.executeUpdate();
				System.out.println("\n새로운 작가 추가: " + vo.getAuthorName());
			} else {
				System.out.println("\n작가가 이미 존재합니다: " + vo.getAuthorName());
				System.out.println();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return insertedCount == 1; // INSERT가 성공했는지 반환
	}

	// 신규 출판사 있는 경우 추가
	@Override
	public boolean insertPublisher(ManagerVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int insertedCount = 0;

		try {
			conn = getConnection();

			// 출판사 존재 여부 확인
			String sql = "SELECT publisher_name FROM publishers WHERE publisher_name = ? AND publisher_email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getPublisherName().trim());
			pstmt.setString(2, vo.getPublisherEmail().trim());
			rs = pstmt.executeQuery();

			if (!rs.next()) { // 출판사가 없는 경우 INSERT 실행
				rs.close();
				pstmt.close();

				String sql2 = "INSERT INTO publishers (publisher_name, publisher_number, publisher_email) VALUES (?, ?, ?)";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, vo.getPublisherName().trim());
				pstmt.setString(2, vo.getPublisherTel().trim());
				pstmt.setString(3, vo.getPublisherEmail().trim());

				insertedCount = pstmt.executeUpdate();
				System.out.println("새로운 출판사 추가: " + vo.getPublisherName());
			} else {
				System.out.println("출판사가 이미 존재합니다: " + vo.getPublisherName());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return insertedCount == 1; // INSERT가 성공했는지 반환
	}

	// 매니저 아이디 검색
	@Override
	public List<ManagerVo> searchmaster(String name_id, String password) {
		List<ManagerVo> list = new ArrayList<>();

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"SELECT name_id, password FROM customers WHERE name_id = 'master' AND password = ?")) {
			// 비밀번호만 매개변수로 설정
			pstmt.setString(1, password);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ManagerVo vo = new ManagerVo(rs.getString("name_id"), rs.getString("password"));
					list.add(vo);
					break; // 첫 번째 결과만 가져옴
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list; // 빈 리스트 반환 가능성 있음
	}
}