package vo;

public class NewsVO {
	
	private int id;
	private String writer;
	private String title;
	private String content;
	private String writedate;
	private int cnt;
	
	public int getId() {
		return id;
	}
	public String getWriter() {
		return writer;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public String getWritedate() {
		return writedate;
	}
	public int getCnt() {
		return cnt;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setWritedate(String writedate) {
		this.writedate = writedate;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	@Override
	public String toString() {
		return "NewsVO [id=" + id + ", writer=" + writer + ", title=" + title + ", content=" + content + ", writedate=" + writedate + ", cnt=" + cnt +"]";
	}	

}
