package com.smu.stock.auth.service;

import com.smu.stock.entity.User;

public interface IAuthService {

	/**
	 * 注册
	 * @param userToAdd
	 * @return
	 */
	User register(User userToAdd);

	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	String login(String username, String password);

	/**
	 * 刷新
	 * @param oldToken
	 * @return
	 */
	String refresh(String oldToken);
}
