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

	public boolean MakeCategory(CategoryVo categoryVo) {
		return 1 == sqlSession.insert("category.insertCg", categoryVo);
	}

	public Long getLength(String id) {
		return sqlSession.selectOne("category.getLenght", id);
	}

	public Long count(Long no) {
		return sqlSession.selectOne("category.count", no);
	}

	public boolean delete(String no) {
		return 1 == sqlSession.delete("category.delete", no);
	}

	
}
