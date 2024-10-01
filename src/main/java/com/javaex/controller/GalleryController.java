package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaex.service.GalleryService;
import com.javaex.util.JsonResult;
import com.javaex.vo.GalleryVo;


@RestController
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;

	// 갤러리 사진 업로드
	@PostMapping("/api/gallery")
	public JsonResult uploadForm(@ModelAttribute GalleryVo galleryVo
							 /* ,@RequestParam("content") String content */) {
		System.out.println("GalleryController.uploadForm()");

		System.out.println("jsx가 준 데이터: "+galleryVo);

		String saveName = galleryService.exeUpload(galleryVo);
		
		System.out.println("서비스가 업데이트 한 후 넘겨준 이름: "+ saveName);
		
		return JsonResult.success(saveName);
	}
	
	// 갤러리 리스트
	@GetMapping("/api/gallery")
	public JsonResult galleryList() {
		System.out.println("GalleryController.galleryList()");
		
		// 서비스에서 리스트 데이터 가져욤
		List<GalleryVo> galleryList = galleryService.exeGalleryList();
		System.out.println("컨트롤러가 뭘 갖고 왔니? "+galleryList);
		return JsonResult.success(galleryList);
	}

}
