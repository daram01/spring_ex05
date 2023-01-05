package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService { 
	public void register(BoardVO board); // 글을 등록하는 역할
	
	public BoardVO get(Long bno); 
	
	public boolean modify(BoardVO board);
	
	public boolean remove(Long bno);
	
//	public List<BoardVO> getList(); // 전체 목록 조회
	
	public List<BoardVO> getList(Criteria cri);
	
	// 전체 데이터 갯수 가져오기
	public int getTotal(Criteria cri);
}
