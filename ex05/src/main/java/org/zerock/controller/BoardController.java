package org.zerock.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@AllArgsConstructor
@Log4j
@RequestMapping("/board/*")
public class BoardController {

	private BoardService service;// 단일 생성자 자동 주입
	
//	// 전체 목록을 가져오는 처리
//	@GetMapping("/list")
//	public void list(Model model) {
//		log.info("list");
//		model.addAttribute("list", service.getList());
//	}
	
//	// 페이징 처리
//	@GetMapping("/list")
//	public void list(Criteria cri, Model model) {
//		log.info("list : " + cri);
//		model.addAttribute("list", service.getList(cri));
//		model.addAttribute("pageMaker", new PageDTO(cri, 123));
//	}
	
	// 페이징 처리 + 전체 데이터 수 가져오기
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		log.info("list : " + cri);
		model.addAttribute("list", service.getList(cri));
		
		int total = service.getTotal(cri);
		log.info("total : " + total);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	// 등록 처리 + 첨부파일
	@PostMapping("/register") // 포스트 방식으로 들어왔을 때
	public String register(BoardVO board, RedirectAttributes rttr) { // 커맨드 객체 BoardVO에서 값을 자동으로 받아서 생성
		// RedirectAttributes의 addFlashAttribute -> 한번 사용할 데이터를 이동하는 페이지에 정보 전달 
		log.info("============================");
		log.info("register : " + board);
		
		if(board.getAttachList() != null) {
			board.getAttachList().forEach(attach -> log.info(attach));
		}
		log.info("============================");
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno()); 
		return "redirect:/board/list";
	}
	
//	// 조회 처리 (기존)
//		@GetMapping({"/get", "/modify"})
//		public void get(@RequestParam("bno") Long bno, Model model) {
//			log.info("/get or modify");
//			model.addAttribute("board", service.get(bno));
//		}
	
	// 조회 처리 (수정 후)
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		log.info("/get or modify");
		model.addAttribute("board", service.get(bno));
	}
	
//	// 수정 처리 (수정 전)
//	@PostMapping("/modify")
//	public String modify(BoardVO board, RedirectAttributes rttr) {
//		log.info("modify : " + board);
//		
//		if(service.modify(board)) {
//			rttr.addFlashAttribute("result", "success");
//		}

//		return "redirect:/board/list";
//	}
	
	// 수정 처리 (수정 후)
	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("modify : " + board);
		
		if(service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/board/list";
	}
	
//	// 삭제 처리 (수정 전)
//	@PostMapping("/remove")
//	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
//		log.info("remove : " + bno);
//		if(service.remove(bno)) {
//			rttr.addFlashAttribute("result", "success");
//		}
//		return "redirect:/board/list";
//	}
	
	// 삭제 처리 (수정 후)
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("remove : " + bno);
		
		// 삭제가 되기 전에 파일의 정보를 가져와야 하기 때문에 service.getAttachList를 호출하여 bno에 관련된 파일 정보를 가져올 수 있다.
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		
		if(service.remove(bno)) { // service.remove는 트랜잭션 처리가 되어있음
			deleteFiles(attachList);
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/board/list";
	}
	
	// 물리적인 경로에서 해당파일을 삭제(테이블에서 데이터가 정확하게 삭제된 이후에)
	private void deleteFiles(List<BoardAttachVO> attachList) {
		
		if(attachList == null || attachList.size() == 0) {
			
			return;
		}
		
		log.info("delete attach files..............");
		log.info(attachList);
		
		attachList.forEach(attach -> {
			
			try {
		        Path file  = Paths.get("C:\\upload\\"+attach.getUploadPath()+"\\" + attach.getUuid()+"_"+ attach.getFileName());
		        
		        Files.deleteIfExists(file);
		        
		        if(Files.probeContentType(file).startsWith("image")) {
		        
		          Path thumbNail = Paths.get("C:\\upload\\"+attach.getUploadPath()+"\\s_" + attach.getUuid()+"_"+ attach.getFileName());
		          
		          Files.delete(thumbNail);
		        }
		        
			} catch(Exception e) {
				log.error("delete file error" + e.getMessage());
			}
		});
	}
	
	
	// 특정한 게시물 번호를 이용해서 첨부파일과 관련된 데이터를 JSON으로 반환
	@GetMapping(value = "/getAttachList",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
		log.info("getArrachList : " + bno);
		return new ResponseEntity<List<BoardAttachVO>>(service.getAttachList(bno), HttpStatus.OK);
	}
	
}
