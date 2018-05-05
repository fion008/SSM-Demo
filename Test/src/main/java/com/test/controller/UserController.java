package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.common.MediaType;
import com.test.entity.User;
import com.test.response.AppResponse;
import com.test.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping(value = "/getUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED)
	public AppResponse getUserDetail(@RequestParam("id") Long id) {
		if (id == null) {
			return null;
		}
		User user = userService.getUser(id);
		return AppResponse.okData(user);
	}

}