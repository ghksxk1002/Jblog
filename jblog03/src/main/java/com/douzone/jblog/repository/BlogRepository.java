package com.douzone.jblog.repository;

import javax.validation.Valid;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.UserVo;

@Repository
public class BlogRepository {
	
	@Autowired
	SqlSession sqlSession;
	
	public void insertIdToBlog(@Valid UserVo vo) {
		sqlSession.insert("user.insertId",vo);
	}
}
