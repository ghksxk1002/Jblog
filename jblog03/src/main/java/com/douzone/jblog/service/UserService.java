package com.douzone.jblog.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.UserVo;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public void join(@Valid UserVo vo) {
		userRepository.insert(vo);
	}
	
	public UserVo getUser(String id) {
		return userRepository.findById(id);
	}

}
