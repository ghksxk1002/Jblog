package com.douzone.jblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets|images).*}")
public class BlogController {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	BlogService blogService;

	// 얘는보안체크해야됨
	@Auth
	@RequestMapping({ "", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(
			Model model, 
			@PathVariable("id") String blogId,
			@PathVariable("categoryNo") Optional<Long> categoryNo,
			@PathVariable("postNo") Optional<Long> postNo) {

		// 블로그 메인 화면의 타이틀, 로고 가져와서 넘겨줌
		BlogVo blogVo = (BlogVo) blogService.getBlog(blogId);
		model.addAttribute("blogVo", blogVo);

		// map에 담자주기 위해 mapper 선언
		Map<String, Object> mapper = new HashMap<String, Object>();
		// url로 넘어온 id
		mapper.put("blogId", blogId);

		if (categoryNo.isPresent()) {
			// url로 카테고리 넘버
			mapper.put("categoryNo", categoryNo.get());
		}

		if (postNo.isPresent()) {
			// url로 넘어온 포스트 넘버
			mapper.put("postNo", postNo.get());
		}
		// mapper blog 서비스로 넘겨서 처리
		Map<String, Object> map = blogService.getBlogAll(mapper);
		
		System.out.println(map);
		// map 뷰로 응답
		model.addAttribute("map", map);
		
		if(blogVo==null) {
			return "redirect:/";
		}

		return "blog/blog-main";
	}

	// 블로그 관리 기본설정 페이지 처리
	@Auth(role = "ADMIN")
	@RequestMapping("/admin/basic")
	public String admin(
			Model model, 
			@PathVariable("id") String id) {

		// url로 넘어온 아이디로 블로그 타이틀 로고 아이디 가져오기
		BlogVo blogVo = (BlogVo) blogService.getBlog(id);
		model.addAttribute("blogVo", blogVo);
		
		return "blog/blog-admin-basic";
	}

	// 블로그 로고, 타이틀 수정
	@Auth(role = "ADMIN")
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
		return "redirect:/" + authUser.getId() + "/admin/basic";
	}

	// 카테고리 삭제
	// 미분류는 삭제 안되게 처리 --> 뷰에서 처리 했음(다른 방법없는지 생각해보자)
	@Auth(role = "ADMIN")
	@RequestMapping(value = "/admin/category/delete/{no}", method = RequestMethod.GET)
	public String delete(
			@PathVariable("no") String no,
			@AuthUser UserVo authUser) {

		blogService.delete(no);

		return "redirect:/" + authUser.getId() + "/admin/category";
	}
	

	// 포스트 지우기
	@Auth(role = "ADMIN")
	@RequestMapping("/delete/{no}")
	public String delete(
			@AuthUser UserVo authUser,
			@PathVariable("no") Long no) {
		blogService.deleteBlog(no);
		return "redirect:/" + authUser.getId();
	}

	// 카테고리 불러오기
	@Auth(role = "ADMIN")
	@RequestMapping("/admin/category")
	public String category(
			CategoryVo categoryVo, 
			@AuthUser UserVo authUser, 
			Model model) {

		/* 다끝나면 이 작업을 서비스로 옵겨보자 */

		// 카테고리 갯수 구해오기
		Long countCg = blogService.getCgLength(authUser.getId());
		model.addAttribute("countCg", countCg);

		// 블로그 정보 타이틀 그림 블로그 아이디 가져와서 세팅 넘겨주기 --> 나중에 서블릿 컨테스트에 담아서 처리해보자
		BlogVo blogVo = (BlogVo) blogService.getBlog(authUser.getId());
		model.addAttribute("blogVo", blogVo);

		// 리스트에 카테고리 no, name, 카테고리 안의 post 수, 설명 담아서 가져오기
		List<CategoryVo> list = blogService.getCategory(authUser.getId());
		model.addAttribute("list", list);
		return "blog/blog-admin-category";
	}

	// 카테고리 추가 --> 카테고리 수정도 생각해보기
	@Auth(role = "ADMIN")
	@RequestMapping(value = "/admin/category", method = RequestMethod.POST)
	public String updateCategory(
			Model model,
			@AuthUser UserVo authUser, 
			@ModelAttribute CategoryVo categoryVo) {

		// 넘오온 정보 뽑아서 Vo에세팅
		categoryVo.setBlogId(authUser.getId());

		// categoryVo 넘겨서 insert처리
		blogService.insertCategory(categoryVo);

		return "redirect:/" + authUser.getId() + "/admin/category";
	}

	// 포스트 쓰는 뷰로가기
	@Auth(role = "ADMIN")
	@RequestMapping("/admin/write")
	public String write(
			Model model, 
			@AuthUser UserVo authUser) {
		
		// 블로그 타이틀 로고 넘겨줌
		BlogVo blogVo = (BlogVo) blogService.getBlog(authUser.getId());
		model.addAttribute("blogVo", blogVo);

		// 카테고리 이름 가져오지
		List<CategoryVo> list = blogService.getCategory(authUser.getId());
		model.addAttribute("list", list);

		return "blog/blog-admin-write";
	}

	// 포스트 추가하기
	@Auth(role = "ADMIN")
	@RequestMapping(value = "/admin/write", method = RequestMethod.POST)
	public String write(
			Model model, 
			@AuthUser UserVo authUser, 
			@ModelAttribute PostVo postVo) {

		// 블로그 타이틀 로고 넘겨줌
		BlogVo blogVo = (BlogVo) blogService.getBlog(authUser.getId());
		model.addAttribute("blogVo", blogVo);

		blogService.addPost(postVo);

		return "redirect:/" + authUser.getId();
	}

}
