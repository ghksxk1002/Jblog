package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	// 카테고리 불러오기
	public List<CategoryVo> getCategory(String blogid) {
		return sqlSession.selectList("category.getCategory", blogid);
	}
	// 카테고리 API
	public CategoryVo getCategoryAPI(String name, String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("blogid", id);
		return sqlSession.selectOne("category.getCategoryAPI", map);
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

	public boolean delete(Long no) {
		return 1 == sqlSession.delete("category.delete", no);
	}

	
}
