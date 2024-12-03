package project1;

// import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoImpl implements UserDao {

	static final String dburl = "jdbc:mysql://localhost:3306/library_db";
	static final String dbuser = "library_user";
	static final String dbpass = "1234";

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dburl, dbuser, dbpass);
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드 실패!");
		}
		return conn;
	}

	@Override
	public List<UserVo> getNewBooks() {
		List<UserVo> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getConnection();

			String sql = "SELECT books.title, authors.author_name, publishers.publisher_name, pub_date, rate, Locations_id, types.type_name, books.id\r\n"
					+ "FROM books JOIN authors ON books.author_id = authors.author_id\r\n"
					+ "			JOIN types ON books.type_id = types.type_id\r\n"
					+ "			JOIN publishers ON books.publisher_id = publishers.publisher_id\r\n"
					+ "ORDER BY Books.id desc limit 5";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String title = rs.getString(1);
				String authorName = rs.getString(2);
				String publisher = rs.getString(3);
				String pubdate = rs.getString(4);
				Integer rate = rs.getInt(5);
				Integer locationId = rs.getInt(6);
				String type = rs.getString(7);
				Integer book_id = rs.getInt(8);

				UserVo vo = new UserVo(title, authorName, publisher, pubdate, rate, locationId, type, book_id);

				list.add(vo);
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
			} catch (Exception e) {
			}
		}

		return list;
	}

	@Override
	public List<UserVo> getList(int book_id) {
		List<UserVo> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "SELECT books.title, authors.author_name, publishers.publisher_name, pub_date, rate, Locations_id, types.type_name, books.id\r\n"
					+ "FROM books JOIN authors ON books.author_id = authors.author_id\r\n"
					+ "			JOIN types ON books.type_id = types.type_id\r\n"
					+ "			JOIN publishers ON books.publisher_id = publishers.publisher_id\r\n"
					+ "WHERE books.id = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, book_id);

			rs = pstmt.executeQuery();

			// 각 레코드를 List<UserVo>로 변환
			while (rs.next()) {
				String title = rs.getString(1);
				String authorName = rs.getString(2);
				String publisher = rs.getString(3);
				String pubdate = rs.getDate(4).toString();
				Integer rate = rs.getInt(5);
				Integer locationId = rs.getInt(6);
				String type = rs.getString(7);
				Integer Book_id = rs.getInt(8);
				UserVo vo = new UserVo(title, authorName, publisher, pubdate, rate, locationId, type, Book_id);

				list.add(vo);

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
			} catch (Exception e) {
			}
		}
		return list;

	}

	@Override
	public List<UserVo> search(String name_id, String password) {
		List<UserVo> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "SELECT name_id, password FROM customers WHERE name_id LIKE ? AND password LIKE ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, name_id);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				if (rs.getString(1).equals(name_id) & rs.getString(2).equals(password)) {
					System.out.println("로그인 성공 !");
					UserVo vo = new UserVo(rs.getString(1), rs.getString(2));
					list.add(vo);
					break;
				}
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
			} catch (Exception e) {
			}
		}
		return list;
	}

	@Override
	public List<UserVo> search2(String author_name) {
		List<UserVo> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "SELECT books.title, authors.author_name, publishers.publisher_name, pub_date, rate, Locations_id, types.type_name, books.id \r\n"
					+ "FROM books JOIN authors ON books.author_id = authors.author_id\r\n"
					+ "			JOIN types ON books.type_id = types.type_id\r\n"
					+ "			JOIN publishers ON books.publisher_id = publishers.publisher_id\r\n"
					+ "WHERE authors.author_name LIKE ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + author_name + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {

				String title = rs.getString(1);
				String authorName = rs.getString(2);
				String publisher = rs.getString(3);
				String pubdate = rs.getString(4);
				Integer rate = rs.getInt(5);
				Integer locationId = rs.getInt(6);
				String type = rs.getString(7);
				Integer book_id = rs.getInt(8);

				UserVo vo = new UserVo(title, authorName, publisher, pubdate, rate, locationId, type, book_id);
				list.add(vo);

			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}

		return list;
	}

	@Override
	public List<UserVo> search3(String title) {
		List<UserVo> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "SELECT books.title, authors.author_name, publishers.publisher_name, pub_date, rate, Locations_id, types.type_name, books.id\r\n"
					+ "FROM books JOIN authors ON books.author_id = authors.author_id\r\n"
					+ "			JOIN types ON books.type_id = types.type_id\r\n"
					+ "			JOIN publishers ON books.publisher_id = publishers.publisher_id\r\n"
					+ "WHERE books.title LIKE ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + title + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {

				String Title = rs.getString(1);
				String authorName = rs.getString(2);
				String publisher = rs.getString(3);
				String pubdate = rs.getString(4);
				Integer rate = rs.getInt(5);
				Integer locationId = rs.getInt(6);
				String type = rs.getString(7);
				Integer book_id = rs.getInt(8);

				UserVo vo = new UserVo(Title, authorName, publisher, pubdate, rate, locationId, type, book_id);
				list.add(vo);
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
			} catch (Exception e) {
			}
		}

		return list;
	}

	@Override
	public List<UserVo> search4(String publisher) {
		List<UserVo> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "SELECT books.title, authors.author_name, publishers.publisher_name, pub_date, rate, Locations_id, types.type_name, books.id\r\n"
					+ "FROM books JOIN authors ON books.author_id = authors.author_id\r\n"
					+ "			JOIN types ON books.type_id = types.type_id\r\n"
					+ "			JOIN publishers ON books.publisher_id = publishers.publisher_id\r\n"
					+ "WHERE publishers.publisher_name LIKE ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, publisher);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				if (rs.getString(3).equals(publisher)) {
					String Title = rs.getString(1);
					String authorName = rs.getString(2);
					String Publisher = rs.getString(3);
					String pubdate = rs.getString(4);
					Integer rate = rs.getInt(5);
					Integer locationId = rs.getInt(6);
					String type = rs.getString(7);
					Integer book_id = rs.getInt(8);

					UserVo vo = new UserVo(Title, authorName, Publisher, pubdate, rate, locationId, type, book_id);
					list.add(vo);
				}
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}

		return list;
	}

	@Override
	public List<UserVo> search5(String type) {
		List<UserVo> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "SELECT books.title, authors.author_name, publishers.publisher_name, pub_date, rate, Locations_id, types.type_name, books.id\r\n"
					+ "FROM books JOIN authors ON books.author_id = authors.author_id\r\n"
					+ "			JOIN types ON books.type_id = types.type_id\r\n"
					+ "			JOIN publishers ON books.publisher_id = publishers.publisher_id\r\n"
					+ "WHERE types.type_name LIKE ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, type);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				if (rs.getString(7).equals(type)) {
					String Title = rs.getString(1);
					String authorName = rs.getString(2);
					String publisher = rs.getString(3);
					String pubdate = rs.getString(4);
					Integer rate = rs.getInt(5);
					Integer locationId = rs.getInt(6);
					String Type = rs.getString(7);
					Integer book_id = rs.getInt(8);

					UserVo vo = new UserVo(Title, authorName, publisher, pubdate, rate, locationId, Type, book_id);
					list.add(vo);
				}
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}

		return list;
	}

	@Override
	public boolean insert(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int insertedCount = 0;

		try {
			conn = getConnection();
			String sql = "INSERT INTO Customers (name, email, phone_number, birth_date, name_id, password) VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPhone_number());
			pstmt.setString(4, vo.getBirth_date());
			pstmt.setString(5, vo.getName_id());
			pstmt.setString(6, vo.getPassword());

			insertedCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 1 == insertedCount;
	}

	@Override
	public List<UserVo> searchRentalBook(int book_id) {
		List<UserVo> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "SELECT id, title FROM books WHERE stock=1 AND id = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, book_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Integer Book_id = rs.getInt(1);

				UserVo vo = new UserVo(Book_id);
				list.add(vo);
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
			} catch (Exception e) {
			}
		}

		return list;
	}

	@Override
	public boolean stockUpdate(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int insertedCount = 0;

		try {
			conn = getConnection();
			String sql = "UPDATE books\r\n" + "SET stock = 0\r\n" + "WHERE id LIKE ?;";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, vo.getId());

			insertedCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 1 == insertedCount;
	}

	@Override
	public List<UserVo> searchReturnBook(int book_id) {
		List<UserVo> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "SELECT id, title\r\n" + "FROM books\r\n" + "WHERE stock=0 AND id LIKE ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, book_id);

			rs = pstmt.executeQuery();
			while (rs.next()) {

				if (rs.getInt(1) == book_id) {

					Integer Book_id = rs.getInt(1);

					UserVo vo = new UserVo(Book_id);
					list.add(vo);
				}
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}

		return list;
	}

	@Override
	public boolean stockUpdate2(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int insertedCount = 0;

		try {
			conn = getConnection();
			String sql = "UPDATE books SET stock = 1 WHERE id LIKE ?;";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, vo.getId());

			insertedCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 1 == insertedCount;
	}

	@Override
	public boolean insertRental(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int insertedCount = 0;

		try {
			conn = getConnection();
			String sql = "INSERT INTO Rental (book_id, customer_id, rental_date, return_expect) VALUES (?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, vo.getId());
			pstmt.setInt(2, vo.getCustomer_id());
			pstmt.setDate(3, vo.getToday());
			pstmt.setDate(4, vo.getDate());

			insertedCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 1 == insertedCount;

	}

	@Override
	public List<UserVo> findCustomerUserId(String name_id) {
		List<UserVo> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "SELECT id FROM customers WHERE name_id LIKE ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, name_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				Integer Book_id = rs.getInt(1);

				UserVo vo = new UserVo(Book_id);
				list.add(vo);
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
			} catch (Exception e) {
			}
		}
		return list;

	}

	@Override
	public boolean Deadline(Date date) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<UserVo> getListC() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 
	 * @Override public boolean Deadline(Date date) { List<UserVo> list = new
	 * ArrayList<>();
	 * 
	 * Connection conn = null; PreparedStatement pstmt = null; ResultSet rs = null;
	 * 
	 * try { conn = getConnection(); String sql =
	 * "SELECT books.id, books.title, authors.author_name, publishers.publisher_name, pub_date, rate, Locations_id, types.type_name\r\n"
	 * + "FROM books JOIN authors ON books.author_id = authors.author_id\r\n" +
	 * "			JOIN types ON books.type_id = types.type_id\r\n" +
	 * "			JOIN publishers ON books.publisher_id = publishers.publisher_id\r\n"
	 * + "WHERE books.id LIKE ? ";
	 * 
	 * pstmt = conn.prepareStatement(sql); pstmt.setInt(1, book_id);
	 * 
	 * rs = pstmt.executeQuery();
	 * 
	 * boolean rentalDate;
	 * 
	 * while (rs.next()) {
	 * 
	 * if (rs.getInt(1) == book_id) {
	 * 
	 * rentalDate = "True"; } else { rentalDate = "False"; } } }
	 * 
	 * catch (SQLException e) { e.printStackTrace(); } finally { try { if (rs !=
	 * null) rs.close(); if (pstmt != null) pstmt.close(); if (conn != null)
	 * conn.close(); } catch (Exception e) { } }
	 * 
	 * return rentalDate; }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @Override public boolean update(UserVo vo) { // TODO Auto-generated method
	 * stub return false; }
	 * 
	 * @Override public UserVo get(Long id) { // TODO Auto-generated method stub
	 * return null; }
	 * 
	 * @Override public boolean delete(Long id) { // TODO Auto-generated method stub
	 * return false; }
	 * 
	 * @Override public List<UserVo> getListC() { // TODO Auto-generated method stub
	 * return null; }
	 * 
	 */

	// 책 반납 처리
	@Override
	public boolean returnBook(int bookId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "UPDATE Rental SET real_return = NOW() WHERE book_id = ? AND real_return IS NULL";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookId);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
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
		return false;
	}

	// 연체일 계산
	public int OverDays(int book_id) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int num = 0;

		try {
			conn = getConnection();

			// 책의 반납 예정일을 가져오는 쿼리 (반납일이 NULL인 경우만)
			String sql = "SELECT return_expect FROM Rental WHERE book_id = ? AND real_return IS NULL";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, book_id);

			rs = pstmt.executeQuery();

			if (rs.next()) { // 결과가 있을 때만 처리
				java.sql.Timestamp returnDate = rs.getTimestamp(1); // 반환 예정일 (시간 포함)

				if (returnDate != null) {
					// 현재 시간과 반납 예정일 차이를 계산
					long diff = System.currentTimeMillis() - returnDate.getTime(); // 밀리초 차이
					num = (int) (diff / (1000 * 60 * 60 * 24)); // 일 단위로 변환

					// 연체일이 음수일 경우, 현재 날짜가 반납 예정일보다 이전인 경우에는 0으로 처리
					if (num < 0)
						num = 0;

				}
			} else {
				// 반납 예정일 정보가 없으면 0일 반환
				num = 0;

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
			} catch (Exception e) {
			}
		}
		return num;

		// new Date().getTime : 현재 시스템 시간을 밀리초 단위로 반환
		// returnDate.getTime() : 데이터베이스에 저장된 return 날자의 시간을 밀리초 단위로 변환
		// diff : 두 시간의 값의 차이를 밀리초 단위로 저장 = 두 날짜 사이에 얼마나 많은 시간이 흘렀는지 밀리초 단위로 표현

		// (diff / (1000 * 60 * 60 * 24) :
		// 밀리초 단위의 차이를 일 단위로 변환하기 위해 1000(밀리초/초),
		// 60(초/분), 60(분/시), 24(시/일)로 나누어줍니다.
		// int : 결과를 정수형으로 변환하여 소수점 이하를 나가리
		// (1000 * 60 * 60 * 24 : 하루 86,400,000초
		// 1000 밀리초 > 1초

		// 긍까 실제 반납일이랑 반납예정일이랑 전부 초단위로 바꿔서 빼준 값을 나누고 int 정수로 받아서 연체된 날짜를 계산
	}

	@Override
	public int getOrInsertAuthorId1(String authorName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOrInsertPublisherId(String authorName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int OverDays(Date returnDate) {
		// TODO Auto-generated method stub
		return 0;
	}
}