package com.douzone.jblog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;



@RestController("userApiController")
@RequestMapping("/user/api")
public class UserController {
	@Autowired
	private UserService userService;
	
	//@ResponseBody
	@GetMapping("/checkid")
	public JsonResult checkid(@RequestParam(value="id", required=true, defaultValue="") String id) {
		System.out.println("[checkid] ID가 입력되었다");
		UserVo userVo = userService.getUser(id);
//		
//		Map<String, Object> map = new HashMap<>();
//		map.put("date", userVo != null);
//		map.put("result", "success");
//		map.put("message", null);
		return JsonResult.success(userVo != null);
	}
}