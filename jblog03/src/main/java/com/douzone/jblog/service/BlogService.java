package com.douzone.jblog.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVo;

@Service
public class BlogService {

	
	@Autowired
	BlogRepository blogRepository;
	
	public Map<String, Object> getBlog(String id) {
		Map<String,Object> map = new HashMap<>();
		BlogVo blog = blogRepository.getBlog(id);
		
		map.put("blog", blog);
		
		return map;
	}

}
