package com.javaex.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {

	// 필드
	@Autowired
	private SqlSession sqlSession;
	// 생성자

	// 메소드 -gs

	// 메소드 일반

	/* 리스트가져오기 */
//	public List<BoardVo> getBoardList() {
//
//		List<BoardVo> boardList = sqlSession.selectList("board.selectList");
//
//		return boardList;
//	}
	
	public List<BoardVo> selectList(Map<String, Object> limitMap) {
		System.out.println("BoardDao.selectList()");
		System.out.println("이것은 다오의 limitMap" + limitMap);
		
		List<BoardVo> boardList = sqlSession.selectList("board.selectList", limitMap);
		System.out.println("리스트의 다오가 불러옴: " + boardList);
		return boardList;
	}
	
	/* 전체 글갯수 */
	public int selectTotalCnt() {
		System.out.println("BoardDao.selectTotalCnt()");
		
		int totalCnt = sqlSession.selectOne("board.selectTotalCnt");
		System.out.println(totalCnt);
		
		return totalCnt;
	}
	/* 전체 글갯수 */
	public int selectTotalCntKeyword(String keyword) {
		System.out.println("BoardDao.selectTotalCntKeyword()");
		
		int totalCnt = sqlSession.selectOne("board.selectTotalCntKeyword", keyword);
		System.out.println(totalCnt+"머라도 좀 줘봐바바바");
		
		return totalCnt;
	}

	/* 게시글 읽기 */
	public BoardVo getBoardRead(int no) {
		// 하나의 게시물에 여러 종류의 데이터가 있을때 게시물 번호 하나만 불러오면 된다.
		BoardVo boardRead = sqlSession.selectOne("board.selectOne", no);

		System.out.println("다오 읽기:" + boardRead);

		return boardRead;
	}

	/* 조회수 증가 */
	public int updateHit(int no) {
		int count = sqlSession.update("board.updateHit", no);

		return count;
	}

	/* 삭제 */
	public int deleteBoard(int no) {
		// xml에 게시글 번호 주고 삭제하라고 시키자
		int count = sqlSession.delete("board.delete", no);

		return count;
	}

	/* 게시글 작성 */
	public int insertBoard(BoardVo boardVo) {

		int count = sqlSession.insert("board.insertBoard", boardVo);

		return count;
	}

	/* 게시글 수정폼 가져오기 */
	public BoardVo getModifyForm(int no) {
		// 게시글 번호 받아서 해당 게시글의 내용을 데이터 베이스에서 가져와
		return sqlSession.selectOne("board.getBoardByNo", no);
	}

	/* 게시글 수정 */
	public int updateBoard(BoardVo boardVo) {

		int count = sqlSession.update("board.update", boardVo);
		System.out.println("수정본 다오 동작:" + boardVo);

		return count;
	}

}
