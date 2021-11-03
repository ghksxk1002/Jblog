package com.douzone.jblog.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.exception.BlogServiceException;
import com.douzone.jblog.security.Auth;
import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets|images).*}")
public class BlogController {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	BlogService blogService;

	// 얘는보안체크해야됨
	@RequestMapping({"", "/{categoryNo}","/{categroyNo}/{postNo}"})
//	@RequestMapping({""})
	public String index(
			Model model,
//			@PathVariable("id") String blogId) {
			@PathVariable("id") String blogId,
			@PathVariable(value = "categoryNo", required = false) String categoryNo,
			@PathVariable(value = "postNo", required = false) String postNo) {
		
		BlogVo blogVo = (BlogVo) blogService.getBlog(blogId);

		model.addAttribute("blogVo", blogVo);
		System.out.println("[블로그메인으로 왔을때]" + blogVo);
		System.out.println("[넘어온 blogId---]" + blogId);
		
		return "blog/blog-main";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/admin/basic")
	public String admin(
			Model model,
			@PathVariable("id") String id) {
		
		BlogVo blogVo = (BlogVo) blogService.getBlog(id);
		System.out.println("[최초에 가져온 블로그 정보]" + blogVo);
		model.addAttribute("blogVo", blogVo);
		return "blog/blog-admin-basic";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	public String update(
			BlogVo blogVo,
			@AuthUser UserVo authUser,
			@RequestParam("title") String title,
			@RequestParam("logo-file") MultipartFile multipartFile) {


		// 이미지 url 받아오기
		try {
			String blogPorflie = blogService.ImageUpload(multipartFile);
			blogVo.setLogo(blogPorflie);
		} catch (BlogServiceException e) {
			System.out.println("이미지가업로드되지 않았습니다.");
		}
		// 수정한 타이틀 세팅 해주기
		blogVo.setTitle(title);

		// 업데이트
		blogService.update(blogVo);
		// 서블릿 컨테스트에 blogVo 보내서 최초로 블로그 방문시에만 DB 갔다오게 하기
		servletContext.setAttribute("blogVo", blogVo);

		// 수정한 블로그 아이디로 리다이텍트
		return "redirect:/" + authUser.getId();
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/admin/category")
	public String category(@PathVariable("id") String id,Model model) {
		
		BlogVo blogVo = (BlogVo) blogService.getBlog(id);
		model.addAttribute("blogVo", blogVo);
		
		return "blog/blog-admin-category";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/admin/write")
	public String write(HttpSession session,Model model) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/user";
		}
		BlogVo blogVo = (BlogVo) blogService.getBlog(authUser.getId());
		model.addAttribute("blogVo", blogVo);
		
		return "blog/blog-admin-write";
	}

}
