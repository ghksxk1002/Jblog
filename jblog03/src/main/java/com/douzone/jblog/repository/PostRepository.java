package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.PostVo;

@Repository
public class PostRepository {

	@Autowired
	SqlSession sqlSession;
	
	public PostVo getName(String id) {
		return sqlSession.selectOne("post.getName",id);
	}

	public boolean addPost(PostVo postVo) {
		return 1 == sqlSession.insert("post.addPost", postVo);
	}
}