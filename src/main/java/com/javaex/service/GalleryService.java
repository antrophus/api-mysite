package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.GalleryDao;
import com.javaex.vo.GalleryVo;

@Service
public class GalleryService {
	
	@Autowired
	private GalleryDao galleryDao;

	public String exeUpload(GalleryVo galleryVo) {
		System.out.println("GalleryService.exeUpload()");

		// 파일저장경로 변수
		String saveDir;

		// 현재 os
		String osName = System.getProperty("os.name").toLowerCase();

		// 사진에 기본정보로 우리가 관리할 정보를 뽑아내야된다 -->db저장
		// 파일 저장 폴더 -os 마다 파일경로 표현 방식이 다르다.
		if (osName.contains("linux")) {
			System.out.println("리눅스");
//			saveDir = "/home/ec2-user/upload";
			saveDir = "/app/upload";
		} else {
			System.out.println("윈도우");
			saveDir = "C:\\javaStudy\\upload";
		}

		// vo에 등록한 img를 file 변수에 담았음: 아래 변수명 통일성때매
		MultipartFile file = galleryVo.getImg();

		// 오리지날 파일명
		String orgName = file.getOriginalFilename();
		System.out.println("orgName: " + orgName);

		// 확장자
		String exeName = orgName.substring(orgName.lastIndexOf("."));
		System.out.println("exeName: " + exeName);

		// 파일사이즈
		long fileSize = file.getSize();
		System.out.println("FileSize: " + fileSize);

		// 저장파일명(겹치지 않아야 한다)
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exeName;
		System.out.println("saveName: " + saveName);

		// 파일 전체 경로+파일명(File.separator: os에 따라 자동으로 역슬래시/슬래시 구분해서 넣어줌)
		String filePath = saveDir + File.separator + saveName;
		System.out.println("filePath: " + filePath);

		// (1)db저장
		// (1-1) 데이타 묶기
		galleryVo.setOrgName(orgName);
		galleryVo.setSaveName(saveName);
		galleryVo.setFilePath(filePath);
		galleryVo.setFileSize(fileSize);

		System.out.println(galleryVo);

		// (1-2) dao를 통해서 db에저장
		// 과제... return 값으로 count가 올거임. 그거 받아서 1이면 hdd에 저장하고 0이면 저장하지 말고...
		int count = galleryDao.insertImage(galleryVo);
		
		System.out.println("과제:" + count +" 개 db저장중.......");
		if(count == 1) {
			// (2)사진을 서버(강남)에 하드디스크에 복사해야된다
			// 파일저장
			try {
				byte[] fileData = file.getBytes();
				OutputStream os = new FileOutputStream(filePath);
				BufferedOutputStream bos = new BufferedOutputStream(os);

				bos.write(fileData);
				bos.close();

			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			return saveName; // 시간+uuid+.jpg
			
		} else {
			return "사진 저장에 실패 하였습니다.";
		}
	}
	
	//갤러리 이미지 리스트 불러오기
	public List<GalleryVo> exeGalleryList(){
		System.out.println("GalleryService.exeGalleryList()");
		
		// 다오에서 리스트 가져오기
		List<GalleryVo> galleryList= galleryDao.selectAllImage();
		
		return galleryList;
		
	}
}
