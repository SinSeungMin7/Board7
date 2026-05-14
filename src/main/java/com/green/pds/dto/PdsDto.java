package com.green.pds.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdsDto {
	// Board 자료
	private int    idx;
	private String title;
	private String content;
	private String writer;
	private String regdate;
	private int    hit;
	
	// Files 자료 -> 파일수
	private int    filescount;
	
	
	// menus 자료
	private String menu_id;
	private String menu_name;
	private int    menu_seq;
}
