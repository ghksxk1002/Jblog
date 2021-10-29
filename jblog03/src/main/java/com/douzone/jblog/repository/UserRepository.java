package com.douzone.jblog.repository;

import javax.validation.Valid;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.UserVo;

@Repository
public class UserRepository {

	@Autowired
	SqlSession sqlSession;
	
	public boolean insert(@Valid UserVo vo) {
		return 1 == sqlSession.insert("user.insert", vo);
	}

	public UserVo findById(String id) {
		return sqlSession.selectOne("user.findById",id);
	}

}
