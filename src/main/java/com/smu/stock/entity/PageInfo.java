package com.smu.stock.entity;

import com.github.pagehelper.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 分页实体类
 * @param <T>
 */
@Data
public class PageInfo<T> implements Serializable {
	//当前页
	private int pageNo;
	//每页的数量
	private int pageSize;
	//总记录数
	private long total;
	//总页数
	private int allPages;
	//结果集
	private List<T> list;
	//是否为第一页
	private boolean isFirstPage = false;
	//是否为最后一页
	private boolean isLastPage = false;
	//导航页码数
	private int navigatePages;
	//所有导航页号
	private List<Integer> navigatePageNums;

	public PageInfo() {
	}

	public PageInfo(List<T> list) {
		if(list instanceof Page){
			Page page = (Page) list;
			this.pageNo = page.getPageNum();
			this.pageSize = page.getPageSize();
			this.allPages = page.getPages();
			this.list = page;
			this.total = page.getTotal();
		} else if(list instanceof Collection){
			//无分页情况下全部显示
			this.pageNo = 1;
			this.pageSize = list.size();
			this.allPages = 1;
			this.list = list;
			this.total = list.size();
		}
		if (list instanceof Collection) {
			//判断页面边界
			judgePageBoundary();
			//计算导航条
			getNavigatePage();
		}
	}

	private void getNavigatePage() {
		this.navigatePageNums = new ArrayList<>();
		for (int index = pageNo-1;index <= this.allPages;index ++) {
			this.navigatePageNums.add(index);
		}
	}

	private void judgePageBoundary() {
		isFirstPage = pageNo == 1;
		isLastPage = pageNo == allPages;
	}
}
