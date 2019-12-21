package com.smu.stock.auth;

import com.smu.stock.entity.AuthUser;
import com.smu.stock.entity.User;
import com.smu.stock.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService implements UserDetailsService {

	@Autowired
	IUserService userService;

	// 加载用户信息
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		try {
			User user = userService.getUserInfoByUserName(userName);
			AuthUser authUser = new AuthUser(user);
			return authUser;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
