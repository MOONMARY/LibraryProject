package project1;

import java.util.Date;
import java.util.List;

// import libra.ManagerVo;

public interface UserDao {
	
	
	// 도서조회
	public List<UserVo> getList(int book_id);						// 책 번호로 해당 책 정보 꺼내오기
	public List<UserVo> search(String name_id, String password);	// 회원 검색
	public List<UserVo> search2(String author_name);				// 작가 검색
	public List<UserVo> search3(String title);						// 제목 검색
	public List<UserVo> search4(String publisher);					// 출판사 검색
	public List<UserVo> search5(String type);						// 장르 검색
	
	
	// 회원가입
	public boolean insert(UserVo vo);
	
	
	// 도서 대여
	public List<UserVo> searchRentalBook(int book_id);
	
	public boolean stockUpdate(UserVo vo);							// stock 0으로 전환
	
	public List<UserVo> findCustomerUserId(String name_id);
	public boolean insertRental(UserVo vo);							// rental 테이블에 정보 추가
	
	
	
	// 도서 반납
	public List<UserVo> searchReturnBook(int book_id);
	public boolean Deadline(Date date);
	public boolean stockUpdate2(UserVo vo);							// stock 1로 전환
	
	boolean returnBook(int bookId);									// SQL real_return에 오늘날짜 찍기
	public int OverDays(int book_id);
	
	// 신규도서 추천
	public List<UserVo> getNewBooks();
	
	
	// 매니저권한 : 도서등록		
	public List<UserVo> getListC();
	
	
	// 작가 id 확인
	public int getOrInsertAuthorId1(String authorName);
	// 출판사 id 확인 
	public int getOrInsertPublisherId(String authorName);
	int OverDays(Date returnDate);
	// public List<ManagerVo> search6(String manager_nameid, String manager_password);
	

}