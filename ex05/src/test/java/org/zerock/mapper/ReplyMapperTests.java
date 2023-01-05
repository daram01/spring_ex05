package org.zerock.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
//	@Test
//	public void testMapper() {
//		log.info(mapper);
//	}
	
	// 댓글 등록 작업
	// 테스트 전에 해당 번호의 게시물이 존재하는지 반드시 확인한다.
	private Long[] bnoArr = { 458774L, 458772L, 458771L, 458770L, 458769L };
//	
//	@Test
//	public void testCreate() {
//		IntStream.rangeClosed(1, 10).forEach(i -> {
//			ReplyVO vo = new ReplyVO();
//			
//			// 게시물의 번호
//			vo.setBno(bnoArr[i % 5]);
//			vo.setReply("댓글 테스트" + i);
//			vo.setReplyer("replyer" + i);
//			
//			mapper.insert(vo);
//		});
//	}
	
	// 댓글 조회
//	@Test
//	public void testRead() {
//		Long targetRno = 5L;
//		ReplyVO vo = mapper.read(targetRno);
//		log.info(vo);
//	}
	
	// 댓글 삭제
//	@Test
//	public void testDelete() {
//		Long targetRno = 2L;
//		mapper.delete(targetRno);
//	}
	
	// 댓글 수정
//	@Test
//	public void testUpdate() {
//		Long targetRno = 10L;
//		ReplyVO vo = mapper.read(targetRno); // 수정할 글이 있는지 없는지 확인하는 작업
//		vo.setReply("Update Reply... ");
//		int count = mapper.update(vo);
//		log.info("UPDATE COUNT : " + count);
//	}
	
	// 현재 데이터베이스에 추가되어 있는 댓글들의 게시물 번호로 확인
	@Test
	public void testList() {
		Criteria cri = new Criteria();
		List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
		replies.forEach(reply -> log.info(reply));
	}
	
	
	
}
