package com.douzone.jblog.repository;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Repository
public class CategoryRepository {

	@Autowired
	SqlSession sqlSession;
	
	public boolean insertToCategory(@Valid UserVo vo) {
		return 1 == sqlSession.insert("category.insert", vo);
	}

	public List<CategoryVo> getCategory(String id) {
		return sqlSession.selectList("category.getCategory", id);
	}
	
}
