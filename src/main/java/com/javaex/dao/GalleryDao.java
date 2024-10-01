package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GalleryVo;

@Repository
public class GalleryDao {
	
	@Autowired
	private SqlSession sqlSession;
	//디비에 갤러리 이미지 업데이트
	public int insertImage(GalleryVo galleryVo) {
		System.out.println("다오가 작동하는가? GalleryDao.insertImage");
		return sqlSession.insert("gallery.insert", galleryVo);
	}

	//디비에서 갤러리 이미지 불러오기
	public List<GalleryVo> selectAllImage() {
		System.out.println("GalleryDao.selectAllImage()");
		
		List<GalleryVo> galleryList = sqlSession.selectList("gallery.selectList");
		
		return galleryList;
	}
}
