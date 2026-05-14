package com.green.board.dto;

public class BoardDto2 {
	// Fields
	private  int      idx;
	private  String   menu_id;
	private  String   title;
	private  String   content;
	private  String   writer;
	private  String   regdate;
	private  int      hit;
	
	// Constructor
	public BoardDto2() {}
	public BoardDto2(int idx, String menu_id, String title, 
			String content, String writer, String regdate, int hit) {
		this.idx = idx;
		this.menu_id = menu_id;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.regdate = regdate;
		this.hit = hit;
	}
	
	// Getter / Setter  : 필수 요소
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	
	//toString
	@Override
	public String toString() {
		return "BoardDto [idx=" + idx + ", menu_id=" + menu_id + ", title=" + title + ", content=" + content
				+ ", writer=" + writer + ", regdate=" + regdate + ", hit=" + hit + "]";
	}
	
}
