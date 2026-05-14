package com.green.paging.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class SearchDto {
	private  int      pageNo;      // 현재페이지 번호 : nowpage  pageNo
	private  int      numOfRows;   // 페이지당 출력할 데이터 갯수 : numOfRows
    private  int      pageSize;    // 화면하단에 출력할 페이지번호의 갯수: 10
                                   // 1 2 3 4 5 6 7 8 9 10 > >>
    private  String   keyword;     // 검색 키워드 
    private  String   searchType;  // 검색 유형 : title, content, writer   
	
    // 페이징된 검색결과 data
    private  Pagination   pagination;  //  페이지네이션 정보 
    
    
    // 기본생성자 : 초기값을 설정 
	public   SearchDto() {
		this.pageNo     =  1;
		this.numOfRows  =  10;
		this.pageSize   =  10;
	}
	
	// 메소드 추가
	public  int  getOffset() {
		return  (this.pageNo - 1) * this.numOfRows;
	}

}







