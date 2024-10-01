package com.javaex.vo;

public class AttachVo {

	// 필드
	private int no;
	private String orgName;
	private String savaName;
	private String filePath;
	private long fileSize;

	// 생성자
	public AttachVo() {
	}

	public AttachVo(String orgName, String savaName, String filePath, long fileSize) {
		super();
		this.orgName = orgName;
		this.savaName = savaName;
		this.filePath = filePath;
		this.fileSize = fileSize;
	}

	public AttachVo(int no, String orgName, String savaName, String filePath, long fileSize) {
		this.no = no;
		this.orgName = orgName;
		this.savaName = savaName;
		this.filePath = filePath;
		this.fileSize = fileSize;
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

	public String getSavaName() {
		return savaName;
	}

	public void setSavaName(String savaName) {
		this.savaName = savaName;
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

	// 메소드 일반
	@Override
	public String toString() {
		return "AttachVo [no=" + no + ", orgName=" + orgName + ", savaName=" + savaName + ", filePath=" + filePath
				+ ", fileSize=" + fileSize + "]";
	}

}
