package com.douzone.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.jblog.vo.BlogVo;

@Controller
@RequestMapping("/blog")
public class BlogController {

	@RequestMapping("")
	public String index(BlogVo blogVo, Model model) {
		System.out.println(blogVo);
		System.out.println("[Id] ="+blogVo.getId());
		model.addAttribute("blogVo", blogVo);
		
		return "blog/blog-main";
	}
}
