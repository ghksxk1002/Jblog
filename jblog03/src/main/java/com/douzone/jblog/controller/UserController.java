package com.douzone.jblog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;



@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("")
	public String index() {
		return "user/login";
	}
	
	@RequestMapping(value="/join", method = RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method = RequestMethod.POST)
	//@ModelAttribute @Valid UserVo vo -- > UserVo의 이름으로 잘못입력한 값을 model에 담아줌 = model.addAttribute("userVo", uservo); 이것과 같다
	public String join(
			@ModelAttribute @Valid UserVo vo, 
			BindingResult result, 
			Model model) {
		System.out.println("[join] 가입하기를 눌렀다");
		// 발리드 값 셋팅
		// 1. reuslt에 에러가 있느닞 확인
		if(result.hasErrors()) {
//			for(ObjectError error : list) {
//				출력되는 에러내용을 사용자에게 알려주기위해 join으로 돌려주어야 한다
//				그러기 위해서는 result 안에 리스트로 들어가 있는 에러내용을 뽑아내야 한는데
//				스프링에서 태그를 지원해준다 jsp로 가자 고고
//				System.err.println(error);	
//			}
//			 result에서 키값뽑아내기 : map 리턴 받기
//			 String에 키값이 object에 에러내용
//			 키로 멥핑되어있는 내용을 계속해서 model에 세팅을 계속해준다
			model.addAllAttributes(result.getModel());
			userService.join(vo);
			// 에러가 있으면 조인으로 돌려야됨
			return "user/join";
		}
		System.out.println(vo);
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinsuccess() {
		return "user/joinsuccess";
	}
}
