package com.smu.stock.controller;

import com.smu.stock.entity.User;
import com.smu.stock.service.IUserService;
import com.smu.stock.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
@ApiIgnore
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	@Autowired
	IUserService userService;

	@RequestMapping(value = "/listUserInfo")
	public Object listUserInfo(HttpServletRequest request) throws Exception {
		PageData pageData = this.getPageData();
		List<User> listUserInfo = userService.listUserInfo(pageData);
		return listUserInfo;
	}
}
