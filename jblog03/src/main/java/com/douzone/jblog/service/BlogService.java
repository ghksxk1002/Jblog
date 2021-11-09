package com.douzone.jblog.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.exception.BlogServiceException;
import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Service
public class BlogService {

	private  String SAVE_PATH = "/upload-blog";
	private  String URL_BASE = "/images";
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	BlogRepository blogRepository;

	public BlogVo getBlog(String id) {
		return blogRepository.getBlog(id);
	}

	public String ImageUpload(MultipartFile multipartFile) {
		// 업로드된 이미지 파일을 저장할 새로운 디렉도리 파일 생성
		File uploadDirectory = new File(SAVE_PATH);

		// 디렉토리 파일이 존제하지 않으면 다시 생성
		if (!uploadDirectory.exists()) {
			uploadDirectory.mkdir();
		}

		// 만약 파일안에 이미지가 없다면 비어있다면 익셉션 드로우
		// 이미지에 관련된 문제가 뜨면 이걸 봐라 종윤이형이 말한
		if (multipartFile.isEmpty()) {
			throw new BlogServiceException("file upload error : image empty");
		}

		// 파일의 이름이 동일한 요청이 들어올수도 있기 때문에 파일 이름을 확장자와 이름으로 나누어
		// 이름을 변경하는 작업을 해주어야 한다
		try {
			String originFilename = multipartFile.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf('.') + 1);
			String saveFileName = generateSavaFileName(extName);

			// OutputStream 으로 넘어오는 이미지 파일 내용을 바이트 단위로 읽어서 저장
			byte[] data = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
			os.write(data);
			os.close();

			return	URL_BASE + "/" + saveFileName;

			//siteRepository.insert(vo);
		} catch (Exception e) {
			throw new BlogServiceException("file upload error:" + e);
		}
	}
	
	private String generateSavaFileName(String extName) {
		// 넘겨줄 파일이름의 초기값을 지정
		String fileName = "";

		Calendar calendar = Calendar.getInstance();

		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += ("." + extName);
		return fileName;
	}
	
	public Map<String, Object> getBlogAll(Map<String, Object> map) {
		
		// 카테고리 리스트 가져오기
		List<CategoryVo> list = categoryRepository.getCategory((String)map.get("blogId"));
		
		Map<String, Object> mapper = new HashMap<String, Object>();
		
		if(map.get("categoryNo") != null) {
			
			List<PostVo> postList = postRepository.getPost((Long)map.get("categoryNo"));
			mapper.put("postList", postList);
			
		} else {
			
			List<PostVo> postList = postRepository.getFirstPostList((String)map.get("blogId"));
			System.out.println("post"+postList);
			mapper.put("postList", postList);
			
		}
		
		if (map.get("postNo") != null) {
			PostVo postVo = postRepository.selectPost((Long)map.get("postNo"));
			mapper.put("postVo", postVo);
			
		} else {
			
			PostVo postVo = postRepository.getFirstPost((String)map.get("blogId"));
			mapper.put("postVo", postVo);
		}
		
		mapper.put("list",list);		
		return mapper;
	}

	// 카테고리 정보 찾아오는 서비스(카테고리No, 카테고리 이름, 포스트수, 블로그Id)
	public List<CategoryVo> getCategory(String id) {
		return categoryRepository.getCategory(id);
	}
	
	// 포스트가져오기 (포스트번호, 타이틀, 내용, 작성일자, 카테고리 번호)
	public List<PostVo> getPost(Long categoryNo) {
		return postRepository.getPost(categoryNo);
	}
	
	public boolean update(BlogVo blogVo) {
		return blogRepository.updateTitleAndImage(blogVo);
	}
		
	public void insertCategory(CategoryVo categoryVo) {
		categoryRepository.MakeCategory(categoryVo);
	}

	public Long getCgLength(String id) {
		return categoryRepository.getLength(id);
	}

	public Long countPost(Long no) {
		return categoryRepository.count(no);
	}

	public boolean delete(String no) {
		return categoryRepository.delete(no);
	}

	
	public PostVo getName(String id) {
		return postRepository.getName(id);
	}

	public boolean addPost(PostVo postVo) {
		return postRepository.addPost(postVo);
	}
}
