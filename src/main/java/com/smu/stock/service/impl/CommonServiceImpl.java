package com.smu.stock.service.impl;

import com.smu.stock.dao.DaoSupport;
import com.smu.stock.service.ICommonService;
import com.smu.stock.service.ITableConfigService;
import com.smu.stock.utils.PageData;
import com.smu.stock.utils.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 公共服务继承类
 */
@Service
public class CommonServiceImpl implements ICommonService {

	@Autowired
	DaoSupport daoSupport;

	@Autowired
	RedisUtils redisUtils;

	@Autowired
	ITableConfigService tableConfigService;

	@Override
	public List<PageData> listCommonInfo(PageData pageData) {
		try {
			pageData.put("operateType","list");
			pageData = constructorOperatePageData(pageData);
			return (List<PageData>) daoSupport.findForList("CommonMapper.listCommonInfo", pageData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public PageData getCommonInfo(PageData pageData) {
		try {
			pageData.put("operateType", "get");
			pageData = constructorOperatePageData(pageData);
			return (PageData) daoSupport.findForObject("CommonMapper.getCommonInfo", pageData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PageData();
	}

	@Override
	public Object saveCommonInfo(PageData pageData) {
		try {
			pageData.put("operateType", "insert");
			constructorOperatePageData(pageData);
			return daoSupport.save("CommonMapper.insertCommonInfo", pageData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Object updateCommonInfo(PageData pageData) {
		try {
			pageData.put("operateType", "update");
			constructorOperatePageData(pageData);
			return daoSupport.update("CommonMapper.updateCommonInfo", pageData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Object deleteCommonInfo(PageData pageData) {
		try {
			pageData.put("operateType", "delete");
			constructorOperatePageData(pageData);
			return daoSupport.delete("CommonMapper.deleteCommonInfo", pageData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


	/**
	 * 构造pageData
	 * @param pageData
	 */
	private PageData constructorOperatePageData(PageData pageData) {
		PageData configPageData = new PageData();
		configPageData.put("pathName", pageData.get("pathInfo"));
		configPageData.put("operateType", pageData.get("operateType"));
		PageData tableConfigInfo = null;
		String redisKey = pageData.get("pathInfo") + "_" + pageData.get("operateType");
		if(ObjectUtils.isEmpty(redisUtils.get(redisKey))) {
			tableConfigInfo = tableConfigService.getTableConfigInfo(configPageData);
			redisUtils.set(redisKey,tableConfigInfo);
		}else {
			tableConfigInfo = redisUtils.get(redisKey,PageData.class);

		}
		pageData.put("tableName", tableConfigInfo.get("tableName"));
		pageData.put("showFields", tableConfigInfo.get("showFields"));
		String showFields = tableConfigInfo.get("showFields").toString();
		String[] showList = showFields.split(",");
		pageData.put("showFiledList",showList);
		String operateFields = tableConfigInfo.get("operateFields").toString();
		String[] operateList = operateFields.split(",");
		List<String> filedList = Arrays.stream(operateList).filter(key -> {
			if(ObjectUtils.isEmpty(pageData.get(key))){
				return false;
			}else{
				return true;
			}
		}).collect(Collectors.toList());
		pageData.put("operateFiledList",filedList);
		return pageData;
	}
}
