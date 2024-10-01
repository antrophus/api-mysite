package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.AttachService;
import com.javaex.util.JsonResult;
import com.javaex.vo.AttachVo2;

@RestController
public class attachController {
	
	@Autowired
	private AttachService attachService;

	@PostMapping("/api/attachs") //데이터로 보냈지만 파라미터타입으로 수신한다. ※주의※
	public JsonResult form(@RequestParam("profileImg") MultipartFile profileImg) {
		System.out.println("attachController.form()");
		System.out.println(profileImg.getOriginalFilename());
		
		String saveName = attachService.exeUpload(profileImg);

		return JsonResult.success(saveName);
	}
	
//	@PostMapping("/api/attachs2")
//	public JsonResult form2(@RequestParam("profileImg") MultipartFile profileImg,
//							@RequestParam("content") String content) {
//		System.out.println("attachController.form2()");
//		System.out.println(profileImg.getOriginalFilename());
//		System.out.println(content);
//		
//		return JsonResult.success("");
//	}
	
	@PostMapping("/api/attachs2")
	public JsonResult form2(@ModelAttribute AttachVo2 attachVo2
							/*,@RequestParam("content") String content*/) {
		System.out.println("attachController.form2()");
		System.out.println(attachVo2);
		
		String saveName = attachService.exeUpload2(attachVo2);
		
		return JsonResult.success(saveName);
	}
}
