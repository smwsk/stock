package com.smu.stock.service;

import com.smu.stock.entity.User;
import com.smu.stock.utils.PageData;

import java.util.List;

public interface IUserService {
	List<User> listUserInfo(PageData pageData) throws Exception;
	User getUserInfo(PageData pageData) throws Exception;
	User getUserInfoByUserName(String userName) throws Exception;

}
