package com.smu.stock.controller;

import com.smu.stock.entity.PageInfo;
import com.smu.stock.exception.BaseException;
import com.smu.stock.service.ICommonService;
import com.smu.stock.utils.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Api(tags = "通用接口", description = "提供通用的Rest API接口")
@RequestMapping(value = "common")
@RestController
public class CommonController extends BaseController{

	@Autowired
	ICommonService commonService;

	@ApiImplicitParam(paramType = "body", dataType = "String")
	@ApiOperation(value = "通用查询列表接口",httpMethod = "POST")
	@RequestMapping(value = "{pathInfo}/listPageInfo")
	public PageInfo listCommonPageInfo(@PathVariable(name = "pathInfo") String pathInfo, HttpServletRequest request){
		PageData pageData = this.getPageData();
		pageData.put("pathInfo",pathInfo);
		List<PageData> pageDataList = commonService.pageCommonInfo(pageData);
		PageInfo<PageData> pageInfo = new PageInfo<>(pageDataList);
		return pageInfo;
	}

	@ApiImplicitParam(paramType = "body", dataType = "String")
	@ApiOperation(value = "通用查询列表接口",httpMethod = "POST")
	@RequestMapping(value = "{pathInfo}/listInfo")
	public List<PageData> listCommonInfo(@PathVariable(name = "pathInfo") String pathInfo, HttpServletRequest request){
		PageData pageData = this.getPageData();
		pageData.put("pathInfo",pathInfo);
		return commonService.listCommonInfo(pageData);
	}

	@ApiOperation(value = "通过获取单个信息接口",httpMethod = "POST")
	@RequestMapping(value = "{pathInfo}/getInfo")
	public PageData getCommonInfo(@PathVariable(name = "pathInfo") String pathInfo, HttpServletRequest request){
		PageData pageData = this.getPageData();
		pageData.put("pathInfo",pathInfo);
		return commonService.getCommonInfo(pageData);
	}

	@ApiOperation(value = "通用新增数据接口",httpMethod = "POST")
	@RequestMapping(value = "{pathInfo}/saveInfo")
	public Object saveCommonInfo(@PathVariable(name = "pathInfo") String pathInfo, HttpServletRequest request){
		PageData pageData = this.getPageData();
		pageData.put("pathInfo",pathInfo);
		return commonService.saveCommonInfo(pageData);
	}

	@ApiOperation(value = "通用更新数据接口",httpMethod = "POST")
	@RequestMapping(value = "{pathInfo}/updateInfo")
	public Object updateCommonInfo(@PathVariable(name = "pathInfo") String pathInfo, HttpServletRequest request){
		PageData pageData = this.getPageData();
		pageData.put("pathInfo",pathInfo);
		return commonService.updateCommonInfo(pageData);
	}

	@ApiOperation(value = "通用删除数据接口",httpMethod = "POST")
	@RequestMapping(value = "{pathInfo}/deleteInfo")
	public Object deleteCommonInfo(@PathVariable(name = "pathInfo") String pathInfo, HttpServletRequest request){
		PageData pageData = this.getPageData();
		if(pageData.isEmpty()){
			throw new BaseException("参数错误，请输入正确的参数");
		}
		pageData.put("pathInfo",pathInfo);
		return commonService.deleteCommonInfo(pageData);
	}
}
