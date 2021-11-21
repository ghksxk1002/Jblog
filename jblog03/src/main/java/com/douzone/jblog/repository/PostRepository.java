package com.douzone.jblog.repository;

import java.util.List;

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

	public List<PostVo> getPost(Long categoryNo) {
		return sqlSession.selectList("post.getPost",categoryNo);
	}
	
	public PostVo getFirstPost(String blogId) {
		return sqlSession.selectOne("post.selectFirstPost", blogId);
	}

	public PostVo selectPost(Long postNo) {
		return sqlSession.selectOne("post.selectPost", postNo);
	}

	public List<PostVo> getFirstPostList(String blogId) {
		return sqlSession.selectList("post.selectPostList", blogId);
	}

	public boolean deletePost(Long no) {
		return 1 == sqlSession.delete("post.deletePost", no);
	}

	public boolean deletAllPost(Long no) {
		return 1 == sqlSession.delete("post.deleteAllPost", no);
	}

}
