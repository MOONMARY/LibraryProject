package test01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.javaex.jdbc.dao.AuthorVo;

public class BookDaoImpl implements BookDao {

	static final String dburl = "jdbc:mysql://localhost:3306/library_db";
	static final String dbuser = "library_user";
	static final String dbpass = "1234";
	//db연결
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dburl,
												dbuser, 
												dbpass);
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드 실패!");
		}
		return conn;
	}
	//책 목록
	@Override
	public List<BookVo> getList() {
		List<BookVo> list = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			
			String sql = "select id, title, author, publisher, pub_date, rate, stock "
					+ "from Books;";
			rs = stmt.executeQuery(sql);
			
			//각 레코드를 List<BookVo>로 변환
			while (rs.next()) {
				int id = rs.getInt(1);
				String authorName = rs.getString(2);
				String authorDesc = rs.getString(3);
				Date regdate = rs.getDate(4);
				
				BookVo vo = new BookVo(id, title, author, publisher, pub_date, rate, stock);
						
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<BookVo> search(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public BookVo get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean insert(BookVo vo) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean update(BookVo vo) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
