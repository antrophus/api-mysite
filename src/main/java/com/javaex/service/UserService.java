package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	/* 회원가입 */
	public int exeJoin(UserVo userVo) {
		int count = userDao.insertUser(userVo);
		System.out.println("가입정보: " + userVo);

		return count;
	}

	/* 로그인 */
	public UserVo exeLogin(UserVo userVo) {

		UserVo authUser = userDao.selectUser(userVo);

		System.out.println("서비스가 다오한테 주는 로그인정보: " + userVo);
		System.out.println("서비스가 받아온 authUser 정보: " + authUser);

		return authUser;
	}

	
	// 회원정보수정폼(1명 데이터가져오기)
		public UserVo exeEditForm(int no) {
			System.out.println("UserService.exeEditForm()");

			UserVo userVo = userDao.userSelectOneByNo(no);
			
			System.out.println("수정할 사람 정보: "+ userVo);
			
			return userVo;
		}
		
		// 회원정보 수정
		public int exeEditUser(UserVo userVo) {
			System.out.println("UserService.exeEditUser()");

			int count = userDao.userUpdate(userVo);
			return count;
		}
		
	 // ID 중복체크 메소드
		/*
		 * public boolean isDuplicateId(String id) {
		 * 
		 * System.out.println("서비스가 받은 중복체크 아이디"+id); UserVo userVo =
		 * userDao.selectById(id); System.out.println("다오가 중복체크해서 준 정보: "+userVo);
		 * return userVo != null; // userVo가 null이 아니면 중복된 ID가 있다는 뜻 }
		 */
    
    /* 아이디 중복 체크 -- 강사님 강의방식 */ 
    public boolean exeIdCheck(String id) {
		System.out.println("UserService.exeIdCheck()");
		System.out.println("서비스가 받은 중복체크 아이디"+id);
		
		int count = userDao.selecUserById(id);
		System.out.println("다오가 서비스에게 넘겨준 숫자:"+count);
		if(count >= 1) {
			return false;
			
		}else {
			return true;
			
		}
	
    }
}