package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	/* 리스트 */
//	public List<BoardVo> exeGetBoardList(){
//		System.out.println("BoardService.exeGetBoardList()");
//		
//		List<BoardVo> boardList = boardDao.getBoardList();
//		
//		return boardList;
//		
//	}
	//리스트3 (페이징 + 검색)
	public Map<String, Object> exeList(int crtPage, String keyword) {
		System.out.println("boardService.exeList()"+ crtPage +"키: "+ keyword);
		
		//////////////////////////////////////////////////////////
		//리스트 가져오기
		//////////////////////////////////////////////////////////
		
		//한 페이지에 10개씩 출력할거임: 한페이지의 출력갯수
		int listCnt = 100;  
		
		//현재페이지 음수일때 계산 3항 연산자
		crtPage = (crtPage > 0) ? crtPage : (crtPage = 1);
			// if(crtPage < 0) {crtPage = 1;}
		
		//startRowNo 구하기: 몇 번 글 가져올건지
		// 1 -> (1번 게시글부터  10개) 2 -> (11번 게시글부터  10개) 3 -> (21번 게시글부터  10개) : 사람이 원하는것
		// 1 -> (0 10) 2 -> (10 10) 3 -> (20 10) : MySQL
		//(1-1)10 = 0
		//(2-1)10 = 10
		//(3-1)10 = 20
		//startRowNo = (crtPage - 1)*listCnt
		int startRowNo = (crtPage -1)*listCnt;
		
		//두개의 데이터를 하나로 묶는다.
		Map<String, Object> limitMap = new HashMap<String, Object>();
		limitMap.put("startRowNo", startRowNo);
		limitMap.put("listCnt", listCnt);
		limitMap.put("keyword", keyword);
		
		List<BoardVo> boardList = boardDao.selectList(limitMap);
		
		//////////////////////////////////////////////////////////
		//페이징 계산(하단 버튼)
		//////////////////////////////////////////////////////////
		
		//페이지당 버튼 갯수
		int pageBtncount = 5;
		
		int totalCnt = boardDao.selectTotalCntKeyword(keyword);
		//endPageBtnNo 마지막 버튼 번호
		// 1  2  3  4  5  >
		// 2 --> (1, 5)
		// 3 --> (1, 5)
		// 4 --> (1, 5)
		// 5 --> (1, 5)
		// 6 --> (6, 10)
		// 7 --> (6, 10)
		//...
		// 10 --> (6, 10)
		// 11 --> (11, 15)
		
		//(1 5) => 올림(1/5)*5  현재페이지를 페이지당버튼개수로 나눔             0.2(1)*5 --> 5
		//(2 5) => 올림(2/5)*5 										   0.4(1)*5 --> 5
		//(3 5) => 올림(3/5)*5 										   0.6(1)*5 --> 5
		//(4 5) => 올림(4/5)*5 										   0.8(1)*5 --> 5
		//(5 5) => 올림(5/5)*5 										   1.0(1)*5 --> 5
		//(6 5) => 올림(6/5)*5 										   1.2(2)*5 --> 10
		//(11 5) => 올림(11/5)*5 										   2.2(3)*5 --> 15
		
		//(올림(crtPage/pageBtncount))*pgaeBtncount
		
		//마지막 버튼 번호
		int endPageBtnNo = (int)Math.ceil(crtPage/(double)pageBtncount)*pageBtncount;
		
		//시작 버튼 번호
		int startPageBtnNo = (endPageBtnNo - pageBtncount) + 1;
		System.out.println(crtPage + " , " + startPageBtnNo + " , " + endPageBtnNo );
		
		//다음 화살표 유무
		boolean next = false;
		if( listCnt * endPageBtnNo < totalCnt) { //페이지다 글갯수(10) * 마지막버튼번호(19) < 전체글 갯수
			next = true;
		}else{
			//다음 화살표가 false일때 마지막 숫자 버튼의 갯수를 정확히 계산
			// 187 -- 19page    187/10 = 18.7 --> 19page올림처리
			endPageBtnNo = (int)Math.ceil(totalCnt/(double)listCnt);
		};
		
		//이전 화살표 유무
		boolean prev = false;
		if(startPageBtnNo != 1) {
			prev = true;
		}
		
		//화면에 표현할 모든 데이터를 묶는다. Map
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("boardList", boardList ); // 리스트 주소
		pMap.put("prev", prev ); // t f 값
		pMap.put("next", next ); // t f 값
		pMap.put("startPageBtnNo", startPageBtnNo);
		pMap.put("endPageBtnNo", endPageBtnNo );
		
		System.out.println("이것은 서비스가 주는 pMap" + pMap);
		
		return pMap;
	}
	
	/* 읽기 */
	public BoardVo exeBoardRead(int no){
		System.out.println("BoardService.exeBoardRead()");
		//조회수 증가
		boardDao.updateHit(no);
		//게시글 가져오기
		BoardVo boardRead = boardDao.getBoardRead(no);
		System.out.println("서비스 읽기:"+ boardRead);
		return boardRead;
	}
	
	/* 삭제 */
	public int deleteBoard(int no) {
		//컨트롤러가 준 no를 다오한테 넘겨주고 다오의 게시글 삭제 메소드 발동!
		int count = boardDao.deleteBoard(no);
		
		return count;
	}
	
	/* 글 작성 */
	public int exeBoardWrite(BoardVo boardVo) {
		
		int count = boardDao.insertBoard(boardVo);
		
		return count;
	}

	/* 글 수정폼 */
	public BoardVo exeModifyForm(int no) {
		//다오 호출해서 게시글 정보 가져와
		BoardVo boardVo = boardDao.getModifyForm(no);
		
		return boardVo;
	}
	
	/* 글 수정 */
	public int exeModifyBoard(BoardVo boardVo) {
		
		int count = boardDao.updateBoard(boardVo);
		
		return count;
		
	}
}
