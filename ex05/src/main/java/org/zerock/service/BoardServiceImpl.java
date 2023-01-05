package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@AllArgsConstructor
@Log4j
@Service
public class BoardServiceImpl implements BoardService {

	private BoardMapper mapper; // 단일 생성자는 자동의존주입이 된다.

	@Override
	public void register(BoardVO board) { // 글을 등록하는 역할
		log.info("register.... : " + board);
		mapper.insertSelectKey(board);
	}

	@Override
	public BoardVO get(Long bno) {
		log.info("get.... : " + bno);
		return mapper.read(bno);
	}

	@Override
	public boolean modify(BoardVO board) {
		log.info("modify.... : " + board);
		return mapper.update(board) == 1;
	}

	@Override
	public boolean remove(Long bno) {
		log.info("remove.... : " + bno);
		return mapper.delete(bno) == 1;
	}


//	@Override
//	public List<BoardVO> getList() {
//		log.info("getList....");
//		return mapper.getList();
//	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("get List with criteria : " + cri);
		return mapper.getListWithPaging(cri);
	}
	
	// 전체 데이터 개수 가져오기
	@Override
	public int getTotal(Criteria cri) {
		log.info("getTotalCount : ");
		return mapper.getTotalCount(cri);
	}
}
