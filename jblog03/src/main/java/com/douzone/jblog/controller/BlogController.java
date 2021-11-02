package com.douzone.jblog.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/blog")
public class BlogController {

	@Autowired
	BlogService blogService;
	
	//얘는보안체크해야됨
	@RequestMapping("/{id}")
	public String index(Model model,
						@PathVariable("id") String id) {
		Map<String, Object> map = blogService.getBlog(id);
		model.addAttribute("map", map);
		System.out.println("[받아온 map]"+ map);
		return "blog/blog-main";
	}
	
	
	@RequestMapping("/admin")
	public String admin(HttpSession session, Model model) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/user";
		}
		Map<String, Object> map = blogService.getBlog(authUser.getId());
		model.addAttribute("map", map);
		return "blog/blog-admin-basic";
	}
	
	@RequestMapping("/category")
	public String category() {
		
		return "blog/blog-admin-category";
	}
	
	@RequestMapping("/write")
	public String write() {
		
		return "blog/blog-admin-write";
	}
	
}
