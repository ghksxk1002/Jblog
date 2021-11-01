package com.douzone.jblog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.jblog.dto.JsonRrsult;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;



@RestController("userApiController")
@RequestMapping("/user/api")
public class UserController {
	@Autowired
	private UserService userService;
	
	//@ResponseBody
	@GetMapping("/checkid")
	public JsonRrsult checkid(@RequestParam(value="id", required=true, defaultValue="") String id) {
		UserVo userVo = userService.getUser(id);
//		
//		Map<String, Object> map = new HashMap<>();
//		map.put("date", userVo != null);
//		map.put("result", "success");
//		map.put("message", null);
		return JsonRrsult.success(userVo != null);
	}
}