package com.javaex.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaex.service.BoardService;
import com.javaex.util.JsonResult;
import com.javaex.vo.BoardVo;


@RestController
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 게시글 목록 불러오기
	@GetMapping("api/boards")
	public JsonResult list(@RequestParam(value="crtpage", required=false, defaultValue="1") int crtPage, 
			   			   @RequestParam(value="keyword", required=false, defaultValue="") String keyword) {
		System.out.println("BoardController.list()");
		Map<String, Object> pMap = boardService.exeList(crtPage, keyword);

		System.out.println("boardList가 뭔가 좀 확인: "+pMap);
		
		if(pMap != null) {
			return JsonResult.success(pMap);
			
		}else {
			return JsonResult.fail("게시글을 불러 올 수 없습니다.");
		}
		
	}
	
	//게시글 삭제
	@DeleteMapping("api/boards/{no}")
	public JsonResult delete(@PathVariable("no") int no) {
		System.out.println("BoardController.delete()");
		int count = boardService.deleteBoard(no);
		
		if(count > 0) {
			return JsonResult.success(no);
		} else {
			return JsonResult.fail("게시글 삭제 실패");
		}
	}
	
	//게시글 작성
	@PostMapping("api/boards")
	public JsonResult write(@RequestBody BoardVo boardVo) {
		System.out.println("BoardController.write()");
		System.out.println("무슨글을 썼나 보자: " + boardVo);
		
		int count = boardService.exeBoardWrite(boardVo);
		
		if(count > 0) {
			return JsonResult.success(boardVo);
			
		} else {
			return JsonResult.fail("게시글 작성 실패");
		} 
	}
	
	//게시글 읽기
	@GetMapping("api/boards/{no}")
	public JsonResult read(@PathVariable("no") int no) {
		System.out.println("BoardController.read()");
		
		BoardVo boardVo = boardService.exeBoardRead(no);
		
		if (boardVo != null) {
			return JsonResult.success(boardVo);
		} else {
			return JsonResult.fail("게시글이 없어");
		}
	}
	
	//게시글 수정
	@PutMapping("api/boards/{no}")
	public JsonResult edit(@PathVariable("no") int no, @RequestBody BoardVo boardVo) {
		System.out.println("BoardController.edit()");
		int count = boardService.exeModifyBoard(boardVo);

	    if (count > 0) {
	        return JsonResult.success(boardVo);
	    } else {
	        return JsonResult.fail("게시글 수정 실패");
	    }
	}
	
}
