package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardAttachMapper;
import org.zerock.mapper.BoardMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Log4j
@Service
public class BoardServiceImpl implements BoardService {

	@Setter(onMethod_= @Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_= @Autowired)
	private BoardAttachMapper attachMapper;

	@Transactional
	@Override
	public void register(BoardVO board) { // 글을 등록하는 역할
		log.info("register.... : " + board);
		
		mapper.insertSelectKey(board);
		
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		
		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
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

	@Transactional
	@Override
	public boolean remove(Long bno) {
		log.info("remove.... : " + bno);
		attachMapper.deleteAll(bno);
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

	// findByBno mapping 작업 - 첨부파일을 화면에 뿌려주는 것
	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		log.info("get Attach list by bno" + bno);
		return attachMapper.findByBno(bno);
	}
}
