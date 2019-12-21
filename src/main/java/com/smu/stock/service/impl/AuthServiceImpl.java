package com.smu.stock.service.impl;

import com.smu.stock.entity.AuthUser;
import com.smu.stock.entity.User;
import com.smu.stock.service.IAuthService;
import com.smu.stock.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class AuthServiceImpl implements IAuthService {

	private AuthenticationManager authenticationManager;

	private UserDetailsService userDetailsService;

	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	public AuthServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	public User register(User userToAdd) {
		// TODO: 保存user到数据库
		return null;
	}

	@Override
	public String login(String userName, String password) {
		// 认证用户，认证失败抛出异常，由JwtAuthError的commence类返回401
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName,password);
		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// 如果认证通过，然后jwt
		AuthUser userDetails = (AuthUser) userDetailsService.loadUserByUsername(userName);
		String token = jwtTokenUtil.generateToken(userDetails.getUser());
		return token;
	}

	@Override
	public String refresh(String oldToken) {
		String newToken = jwtTokenUtil.refreshToken(oldToken);
		return newToken;
	}
}
