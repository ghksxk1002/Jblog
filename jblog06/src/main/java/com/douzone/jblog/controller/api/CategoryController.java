package com.douzone.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Controller("CategoryController")
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	BlogService blogService;
	
	@ResponseBody
	@PostMapping("/list")
	public JsonResult list(@AuthUser UserVo authUser) {
		List<CategoryVo> list = blogService.getCategory(authUser.getId());
		return JsonResult.success(list);
	}
	
	@ResponseBody
	@PostMapping("/add")
	public JsonResult add(
			@AuthUser UserVo authUser,
			@RequestBody CategoryVo vo) {
		
		
		vo.setBlogId(authUser.getId());
		System.out.println(vo);
		blogService.insertCategory(vo);
		
		return JsonResult.success(vo);
	}
	
	@ResponseBody
	@RequestMapping("/delete/{no}")
	public JsonResult delate(
			@AuthUser UserVo authUser,
			@PathVariable("no") Long no) {
		
		
		//vo.setBlogId(authUser.getId());
		System.out.println(no);
		blogService.deletePost(no);
		blogService.delete(no);
		
		return JsonResult.success(no);
	}
	
}
