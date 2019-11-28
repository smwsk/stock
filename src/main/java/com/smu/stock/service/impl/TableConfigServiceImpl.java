package com.smu.stock.service.impl;

import com.smu.stock.dao.DaoSupport;
import com.smu.stock.exception.BaseException;
import com.smu.stock.service.ITableConfigService;
import com.smu.stock.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableConfigServiceImpl implements ITableConfigService {

	@Autowired
	DaoSupport daoSupport;

	@Override
	public PageData getTableConfigInfo(PageData pageData) {
		try {
			return (PageData) daoSupport.findForObject("TableConfigMapper.getTableConfigInfo",pageData);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException("系统异常：sql语句有误");
		}
	}
}
