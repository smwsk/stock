package com.smu.stock.service;

import com.smu.stock.utils.PageData;

import java.util.List;

public interface ICommonService {

	List<PageData> listCommonInfo(PageData pageData);

	PageData getCommonInfo(PageData pageData);

	Object saveCommonInfo(PageData pageData);

	Object updateCommonInfo(PageData pageData);

	Object deleteCommonInfo(PageData pageData);

}
