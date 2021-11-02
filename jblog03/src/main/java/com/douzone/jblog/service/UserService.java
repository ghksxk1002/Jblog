package com.douzone.jblog.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.UserVo;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BlogRepository blogRepository;
	
	public void join(@Valid UserVo vo) {
		userRepository.insert(vo);
		blogRepository.insertIdToBlog(vo);
	}
	
	public UserVo getUser(String id, String password) {
		return userRepository.findByIdAndPassword(id, password);
	}
	
	public UserVo getUser(String id) {
		return userRepository.findById(id);
	}

}
