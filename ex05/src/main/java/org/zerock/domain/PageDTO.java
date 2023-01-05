package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	private int startPage; // 페이지의 시작 번호
	private int endPage; // 페이지의 끝 번호
	private boolean prev, next; // 이전 페이지 , 다음 페이지
	
	private int total; // 전체 페이지
	private Criteria cri;
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10; // 페이징의 끝번호
		this.startPage = this.endPage - 9; // 페이징의 시작번호
		
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount())); // 실제 끝페이지가 몇 번까지 되는지 계산
		
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1; // 이전페이지
		
		this.next = this.endPage < realEnd; // 다음페이지 
	}
}
