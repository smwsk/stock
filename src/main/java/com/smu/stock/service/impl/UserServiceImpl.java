package com.smu.stock.service.impl;

import com.smu.stock.dao.DaoSupport;
import com.smu.stock.entity.User;
import com.smu.stock.service.IUserService;
import com.smu.stock.utils.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

	@Resource(name = "daoSupport")
	DaoSupport daoSupport;

	public List<User> listUserInfo(PageData pageData) throws Exception {
		return (List<User>) daoSupport.findForList("UserMapper.listUserInfo", pageData);
	}

	@Override
	public User getUserInfo(PageData pageData) throws Exception {
		return (User) daoSupport.findForObject("UserMapper.getUserInfo", pageData);
	}

	@Override
	public User getUserInfoByUserName(String userName) throws Exception {
		PageData pageData = new PageData();
		pageData.put("userName",userName);
		return getUserInfo(pageData);
	}
}
