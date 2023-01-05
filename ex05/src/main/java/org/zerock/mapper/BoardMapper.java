package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper { // DAO의 역할을 하는 인터페이스.
	
	// 전체 게시글 불러오기
	public List<BoardVO> getList();
	
	// 페이징 처리
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	// 글 등록하기
	public void insert(BoardVO board);

	public void insertSelectKey(BoardVO board);
	
	// 특정글 하나만 조회하는 것
	public BoardVO read(Long bon);
	
	// 게시글 삭제
	public int delete(Long bno);
	
	// 업데이트 처리. 제목,내용,작성자를 수정할 때 최종 수정시간을 현재 시간으로 수정하는 것
	public int update(BoardVO board);
	
	// 전체 데이터의 개수 처리
	public int getTotalCount(Criteria cri);
	
	// 댓글 수 처리
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
}
