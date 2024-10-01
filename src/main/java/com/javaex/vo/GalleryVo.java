package com.javaex.vo;

import org.springframework.web.multipart.MultipartFile;

public class GalleryVo {

	// 필드
	private int no;
	private String orgName;
	private String saveName;
	private String filePath;
	private long fileSize;

	private String content;
	private MultipartFile img;

	private int userNo; // 회원 번호 (users 테이블의 FK)
	private String author; // 작성자 이름 (users 테이블의 name 필드)

	// 생성자
	public GalleryVo() {
		super();
	}

	public GalleryVo(int no, String orgName, String saveName, String filePath, long fileSize, String content,
			MultipartFile img) {
		super();
		this.no = no;
		this.orgName = orgName;
		this.saveName = saveName;
		this.filePath = filePath;
		this.fileSize = fileSize;
		this.content = content;
		this.img = img;
	}

	public GalleryVo(int no, String orgName, String saveName, String filePath, long fileSize, String content,
			MultipartFile img, int userNo, String author) {
		super();
		this.no = no;
		this.orgName = orgName;
		this.saveName = saveName;
		this.filePath = filePath;
		this.fileSize = fileSize;
		this.content = content;
		this.img = img;
		this.userNo = userNo;
		this.author = author;
	}

	// 메소드 gs
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MultipartFile getImg() {
		return img;
	}

	public void setImg(MultipartFile img) {
		this.img = img;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	// 메소드 일반
	@Override
	public String toString() {
		return "GalleryVo [no=" + no + ", orgName=" + orgName + ", saveName=" + saveName + ", filePath=" + filePath
				+ ", fileSize=" + fileSize + ", content=" + content + ", img=" + img + ", userNo=" + userNo
				+ ", author=" + author + "]";
	}

	
	

}
