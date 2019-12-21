package com.smu.stock.service;

import com.github.pagehelper.Page;
import com.smu.stock.utils.PageData;

import java.util.List;

public interface ICommonService {

	/**
	 * 获取分页接口
	 * @param pageData
	 * @return
	 */
	List<PageData> pageCommonInfo(PageData pageData);

	List<PageData> listCommonInfo(PageData pageData);

	PageData getCommonInfo(PageData pageData);

	Object saveCommonInfo(PageData pageData);

	Object updateCommonInfo(PageData pageData);

	Object deleteCommonInfo(PageData pageData);

}
