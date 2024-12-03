package project1;

import java.util.Date;

public class UserVo {

	private String title;
	private String authorName;
	private String publisher;
	private String pubdate;
	private Integer rate;
	private Integer locationId;
	private String type;
	
	private String name;
	private String email;
	private String phone_number;
	private String birth_date;
	private String name_id;
	private String password;

	private Integer id;
	private Integer customer_id;
	

	

	/*
	 * private Date date; private Date today;
	 */

	private java.sql.Date today;
	private java.sql.Date date;



	
	
	public UserVo(int id) {
		super();
		this.id = id;
	}



	public UserVo(String title, String authorName) {
		super();
		this.title = title;
		this.authorName = authorName;

	}

	public UserVo(String title, String authorName, String publisher) {
		super();
		this.title = title;
		this.authorName = authorName;
		this.publisher = publisher;

	}

	public UserVo(String title, String authorName, String publisher, Integer rate) {
		super();
		this.title = title;
		this.authorName = authorName;
		this.publisher = publisher;
		this.rate = rate;
	}

	public UserVo(Integer id, Integer customer_id, Date today, Date date) {
		super();
		this.id = id;
		this.customer_id = customer_id;
		this.today = (java.sql.Date) today;
		this.date = (java.sql.Date) date;
	}

	public UserVo(Integer id, Integer customer_id, Integer rate, Integer locationId) {
		super();
		this.id = id;
		this.customer_id = customer_id;
		this.rate = rate;
		this.locationId = locationId;
	}

	public UserVo(String title, String authorName, String publisher, String type, Integer rate) {
		super();
		this.title = title;
		this.authorName = authorName;
		this.publisher = publisher;
		this.type = type;
		this.rate = rate;
	}

	public UserVo(String name, String email, String phone_number, String birth_date, String name_id, String password) {
		super();
		this.name = name;
		this.email = email;
		this.phone_number = phone_number;
		this.birth_date = birth_date;
		this.name_id = name_id;
		this.password = password;

	}

	public UserVo(int id, String title, String authorName, String publisher, String pubdate, int rate, int locationId,
			String type) {
		super();
		this.id = id;
		this.title = title;
		this.authorName = authorName;
		this.publisher = publisher;
		this.pubdate = pubdate;
		this.locationId = locationId;
		this.type = type;
		this.rate = rate;

	}

	public UserVo(String title, String authorName, String publisher, String pubdate, int rate, int locationId,
			String type) {
		super();
		this.title = title;
		this.authorName = authorName;
		this.publisher = publisher;
		this.pubdate = pubdate;
		this.locationId = locationId;
		this.type = type;
		this.rate = rate;

	}

	public UserVo(String title, String authorName, String publisher, String pubdate, int rate, int locationId,
			String type, int id) {
		super();
		this.title = title;
		this.authorName = authorName;
		this.publisher = publisher;
		this.pubdate = pubdate;
		this.locationId = locationId;
		this.type = type;
		this.rate = rate;
		this.id = id;

	}
	
	


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public String getName_id() {
		return name_id;
	}

	public void setName_id(String name_id) {
		this.name_id = name_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	/*
	 * public Date getDate() { return date; }
	 * 
	 * public void setDate(Date date) { this.date = date; }
	 * 
	 * public Date getToday() { return today; }
	 * 
	 * public void setToday(Date today) { this.today = today; }
	 */

	// Getter 및 Setter도 수정
	public java.sql.Date getToday() {
		return today;
	}

	public void setToday(java.sql.Date today) {
		this.today = today;
	}

	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "UserVo [제목=" + title + ", 작가=" + authorName + ", 출판사=" + publisher + ", 출판일=" + pubdate + ", 별점=" + rate
				+ ", 위치=" + locationId + ", 장르=" + type + ", 도서 번호=" + id + "]";

	}

}