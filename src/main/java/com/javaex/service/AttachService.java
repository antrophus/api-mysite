package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.vo.AttachVo;
import com.javaex.vo.AttachVo2;

@Service
public class AttachService {
	
	public String exeUpload2(AttachVo2 attachVo2) {
		System.out.println("AttachService.upload2");
		
		//파일저장경로 변수
		String saveDir;
		
		//현재 os
		String osName = System.getProperty("os.name").toLowerCase();
		
		//사진에 기본정보로 우리가 관리할 정보를 뽑아내야된다 -->db저장
		//파일 저장 폴더 -os 마다 파일경로 표현 방식이 다르다.
		if(osName.contains("linux")) {
			System.out.println("리눅스");
			saveDir = "/app/upload";
		}else {
			System.out.println("윈도우");
			saveDir = "C:\\javaStudy\\upload";
		}
		
		//vo에 등록한 img를 file 변수에 담았음: 아래 변수명 통일성때매
		MultipartFile file = attachVo2.getImg();
		
		//오리지날 파일명
		String orgName = file.getOriginalFilename();
		System.out.println("orgName: " + orgName );
		
		//확장자
		String exeName = orgName.substring(orgName.lastIndexOf("."));
		System.out.println("exeName: " + exeName);
		
		//파일사이즈
		long fileSize = file.getSize();
		System.out.println("FileSize: " + fileSize);
		
		//저장파일명(겹치지 않아야 한다)
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString()+exeName;
		System.out.println("saveName: " + saveName);
		
		//파일 전체 경로+파일명(File.separator: os에 따라 자동으로 역슬래시/슬래시 구분해서 넣어줌)
		String filePath = saveDir + File.separator + saveName;
		System.out.println("filePath: " + filePath);
		
		//(1)db저장
		//(1-1) 데이타 묶기
//		AttachVo attachVo= new AttachVo(orgName, saveName, filePath, fileSize);
		attachVo2.setOrgName(orgName);
		attachVo2.setSaveName(saveName);
		attachVo2.setFilePath(filePath);
		attachVo2.setFileSize(fileSize);
		
		System.out.println(attachVo2);
		
        //(1-2) dao를 통해서 db에저장
		//과제.....
		System.out.println("과제:" + "db저장중.......");
		
		
		//(2)사진을 서버(강남)에 하드디스크에 복사해야된다
		//파일저장
		try {
			byte[] fileData = file.getBytes();
			OutputStream os = new FileOutputStream(filePath);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			
			bos.write(fileData);
			bos.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return saveName;  //시간+uuid+.jpg
	}
	
	
	public String exeUpload(MultipartFile file) {
		System.out.println("AttachService.upload");
		
		//사진에 기본정보로 우리가 관리할 정보를 뽑아내야된다 -->db저장
		//파일 저장 폴더
		String saveDir = "C:\\javaStudy\\upload";
		
		
		//오리지날 파일명
		String orgName = file.getOriginalFilename();
		System.out.println("orgName: " + orgName );
		
		//확장자
		String exeName = orgName.substring(orgName.lastIndexOf("."));
		System.out.println("exeName: " + exeName);
		
		//파일사이즈
		long fileSize = file.getSize();
		System.out.println("FileSize: " + fileSize);
		
		//저장파일명(겹치지 않아야 한다)
		String savaName = System.currentTimeMillis() + UUID.randomUUID().toString()+exeName;
		System.out.println("savaName: " + savaName);
		
		//파일 전체 경로+파일명
		String filePath = saveDir + "\\" + savaName;
		System.out.println("filePath: " + filePath);
		
		//(1)db저장
		//(1-1) 데이타 묶기
		AttachVo attachVo= new AttachVo(orgName, savaName, filePath, fileSize);
		System.out.println(attachVo);
		
        //(1-2) dao를 통해서 db에저장
		//과제.....
		System.out.println("과제:" + "db저장중.......");
		
		
		//(2)사진을 서버(강남)에 하드디스크에 복사해야된다
		//파일저장
		try {
			byte[] fileData = file.getBytes();
			OutputStream os = new FileOutputStream(filePath);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			
			bos.write(fileData);
			bos.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return savaName;  //시간+uuid+.jpg
	}

}
