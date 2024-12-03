package test01;

public class BookVo {
	private int id;
	private String title;
	private String author;
	private String publisher;
	private String pubDate;
	private int rate;
	private int stock;
	private int locationsId;
	private int typeId;
	private int publisherId;
	private int authorId;
	
	public BookVo() {
		
	}

	public BookVo(int id, String title, String author, String publisher, String pubDate, int rate, int stock,
			int locationsId, int typeId, int publisherId, int authorId) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.pubDate = pubDate;
		this.rate = rate;
		this.stock = stock;
		this.locationsId = locationsId;
		this.typeId = typeId;
		this.publisherId = publisherId;
		this.authorId = authorId;
	}

	public BookVo(String title, String author, String publisher, String pubDate, int rate, int stock, int locationsId,
			int typeId, int publisherId, int authorId) {
		super();
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.pubDate = pubDate;
		this.rate = rate;
		this.stock = stock;
		this.locationsId = locationsId;
		this.typeId = typeId;
		this.publisherId = publisherId;
		this.authorId = authorId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	
	
}
