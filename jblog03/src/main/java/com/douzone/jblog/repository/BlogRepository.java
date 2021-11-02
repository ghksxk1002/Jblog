package com.douzone.jblog.repository;

import javax.validation.Valid;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.UserVo;

@Repository
public class BlogRepository {

	@Autowired
	SqlSession sqlSession;

	public void insertIdToBlog(@Valid UserVo vo) { // 회원가입시 가입한 ID로 블로그 테이블에 ID 하나 생성
		sqlSession.insert("blog.insertId", vo);
	}

	public BlogVo getBlog(String id) {
		return sqlSession.selectOne("blog.getBlog", id);
	}
	
	
}
