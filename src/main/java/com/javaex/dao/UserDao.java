package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;

	/* 회원가입 */
	public int insertUser(UserVo userVo) {

		int count = sqlSession.insert("user.insert", userVo);
		System.out.println("UserDao가 회원가입" + count + "건 service로 넘기기");
		return count;

	}

	/* 로그인 */
	public UserVo selectUser(UserVo userVo) {
		System.out.println("다오가 디비에 넘겨주는 로그인 정보" + userVo);
		UserVo authUser = sqlSession.selectOne("user.selectByIdPw", userVo);
		System.out.println("디비가 다오에게 주는 authUser: " + authUser);

		return authUser;

	}

	// no로 한명데이터 가져오기(회원정보수정 폼)
	public UserVo userSelectOneByNo(int no) {
		System.out.println("UserDao.userSelectOneByNo()");

		UserVo userVo = sqlSession.selectOne("user.selectOneByNo", no);

		System.out.println("다오가 디비에서 갖고온 수정할 데이터: " + userVo);

		return userVo;
	}

	// 회원 정보 수정하기
	public int updateUser(int no) {
		System.out.println("UserDao.updateUser()");
		return sqlSession.update("user.updateUser", no);

	}

	// 수정(회원정보수정)
	public int userUpdate(UserVo userVo) {
		System.out.println("UserDao.userUpdate()");

		int count = sqlSession.update("user.update", userVo);
		return count;
	}

	/*
	 * // ID로 사용자 조회 public UserVo selectById(String id) {
	 * System.out.println("중복체크 하려고 입력한(다오에게) id: " + id); return
	 * sqlSession.selectOne("user.selectById", id); // MyBatis 매퍼에서
	 * "user.selectById" 매핑 }
	 */

	// id로 데이터가져오기-id사용여부 체크할때 사용
	public int selecUserById(String id) {
		System.out.println("UserDao.selecUserById()");
		System.out.println("다오가 받은 중복체크 아이디" + id);
		int count = sqlSession.selectOne("user.selectByIdchk", id);
		System.out.println("다오가 디비에서 받은 숫자: " + count);
		return count;
	}

}