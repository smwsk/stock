package com.smu.stock.service;

import com.smu.stock.utils.PageData;

import java.util.List;

public interface IUserService {
	List<PageData> getListUserInfo(PageData pageData) throws Exception;
}
