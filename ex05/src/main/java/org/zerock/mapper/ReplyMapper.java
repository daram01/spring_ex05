package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

public interface ReplyMapper {

	// 댓글 등록
	public int insert(ReplyVO vo);
	
	// 댓글 조회
	public ReplyVO read(Long bno);
	
	// 댓글 삭제
	public int delete (Long rno);
	
	// 댓글 수정
	public int update(ReplyVO reply);
	
	// 댓글 목록 + 페이징 처리 + 특정한 게시물의 댓글만 대상으로 한다.
	public List<ReplyVO> getListWithPaging(
			@Param("cri") Criteria cri,
			@Param("bno") Long bno);
}
