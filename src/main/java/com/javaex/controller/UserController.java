package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaex.service.UserService;
import com.javaex.util.JsonResult;
import com.javaex.util.JwtUtil;
import com.javaex.vo.UserVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class UserController {

	// 필드
	@Autowired
	private UserService userService;

	/* 로그인 */
	@PostMapping("/api/users/login")
	public JsonResult login(@RequestBody UserVo userVo, HttpServletResponse response) {
		System.out.println("UserController.login()");

		UserVo authUser = userService.exeLogin(userVo);
		System.out.println("서비스가 컨트롤러에게 넘겨준 authUser: " + authUser);

		if (authUser != null) {// 로그인 성공
			// 토큰을 만들고 "응답문서의 헤더"에 토큰을 붙여서 보낸다.
			JwtUtil.createTokenAndSetHeader(response, "" + authUser.getNo());
			return JsonResult.success(authUser);
		} else {
			return JsonResult.fail("로그인 실패");
		}
	}

	// 회원 가입시 아이디 중복체크를 위한 메소드
	@PostMapping("/api/users/checkid")
	public JsonResult checkId(@RequestParam String id) {
		System.out.println("UserController.checkId()");
		System.out.println("입력받은 중복아이디: " + id);

		// userService에 checkId 메소드를 구현해 호출해야 함
		boolean can = userService.exeIdCheck(id);
		// true 일 경우 입력값과 중복되는 데이터가 있음

		System.out.println(can);

		if (can != true) {
			return JsonResult.fail("사용할 수 없는 아이디입니다.");
		} else {
			return JsonResult.success(can);
		}
	}

	/* 회원가입 */
	@PostMapping("/api/users")
	public JsonResult join(@RequestBody UserVo uservo) {

		System.out.println("join 실행: 회원 가입 실행");

		int count = userService.exeJoin(uservo);

		if (count != 1) {
			return JsonResult.fail("회원가입 실패");
		} else {
			return JsonResult.success(count);
		}
	}

	/* 회원정보수정폼 */
	@GetMapping("api/users/me")
	public JsonResult editForm(HttpServletRequest request) {
		System.out.println("UserController.editForm()");
		System.out.println("리액트가 axios로 뭐 보냈나? " + request);

		// 요청 헤더에서 토큰을 꺼내서 유효성을 체크한 후 정상이면 no값을 꺼내준다.
		int no = JwtUtil.getNoFromHeader(request);
		System.out.println("no값 출력: " + no);

		if (no != -1) { // 토큰 유효함
			UserVo userVo = userService.exeEditForm(no);
			return JsonResult.success(userVo);

		} else {
			return JsonResult.fail("토큰X, 비로그인, 변조");
		}
	}

	/* 회원 정보 수정 */
	@PutMapping("api/users/me")
	public JsonResult editUser(@RequestBody UserVo userVo, HttpServletRequest request) {
		System.out.println("UserController.editUser()");

		// no값 출력
		int no = JwtUtil.getNoFromHeader(request);

		if (no != -1) { // 토큰 유효함
			System.out.println("토큰에서 받은 no값: " + no);
			System.out.println("jsx에서 받아온 수정 정보: " + userVo);
			userVo.setNo(no);
			System.out.println("수정정보 + 토큰 no값: " + userVo);
			int count = userService.exeEditUser(userVo);

			if (count == 1) {
				// 정상적으로 수정되었을 때
				userVo.setPassword(null); // authUser 정보에 패스워드를 넘겨줄 필요가 없으니 지움.
				userVo.setGender(null); // 위와 같은 이유로 지움.
				return JsonResult.success(userVo);
			} else {
				return JsonResult.fail("수정실패");
			}

		} else {// 토큰이 유효하지 않음
			return JsonResult.fail("토큰X, 비로그인, 변조");
		}
	}
}