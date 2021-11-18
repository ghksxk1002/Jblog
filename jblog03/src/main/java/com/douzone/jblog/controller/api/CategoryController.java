package com.douzone.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
	public JsonResult checkname(
			@AuthUser UserVo authUser) {
		System.out.println("[checkname가] 입력되었다"+authUser.getId());
		//vo = blogService.getCategoryAPI(name, userVo.getId());
		// 블로그 정보 타이틀 그림 블로그 아이디 가져와서 세팅 넘겨주기 --> 나중에 서블릿 컨테스트에 담아서 처리해보자
		// 리스트에 카테고리 no, name, 카테고리 안의 post 수, 설명 담아서 가져오기
		List<CategoryVo> list = blogService.getCategory(authUser.getId());
		System.out.println(list);
		return JsonResult.success(list);
	}
	
}
