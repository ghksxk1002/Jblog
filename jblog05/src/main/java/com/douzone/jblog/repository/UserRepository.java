package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.exception.UserRepositoryException;
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

	public UserVo findByIdAndPassword(String id, String password) throws UserRepositoryException {
		Map<String, String> map = new HashMap<>();
		map.put("i", id);
		map.put("p", password);

		return sqlSession.selectOne("findByEmailAndPassword", map);
}

	public void insertIdToBlog(@Valid UserVo vo) {
		sqlSession.insert("user.insertId",vo);
	}

}
