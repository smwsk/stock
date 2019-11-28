package com.smu.stock.service.impl;

import com.smu.stock.dao.DaoSupport;
import com.smu.stock.service.IUserService;
import com.smu.stock.utils.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

	@Resource(name = "daoSupport")
	DaoSupport daoSupport;

	public List<PageData> getListUserInfo(PageData pageData) throws Exception {
		return (List<PageData>) daoSupport.findForList("UserMapper.listUserInfo", pageData);
	}
}
