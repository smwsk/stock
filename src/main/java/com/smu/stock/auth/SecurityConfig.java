package com.smu.stock.auth;

import com.smu.stock.exception.JwtAuthError;
import com.smu.stock.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity //添加security过滤器
@EnableGlobalMethodSecurity(prePostEnabled = true) // 可以在controller方法上配置权限
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/*加载用户信息*/
	@Autowired
	private UserDetailsService userDetailsService;

	// 权限不足错误信息处理，包含认证错误与鉴权错误处理
	@Autowired
	private JwtAuthError jwtAuthError;

	@Autowired
	private JwtAuthFilter jwtAuthFilter;

	/**
	 * 密码加密
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


	/*获取authenticationManage(认证管理器)*/
	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}


	/**
	 * 配置用户信息加载
	 * @param authenticationManagerBuilder
	 */
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService);
	}

	/**
	 * 配置接口控制限权
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			// 基于token，不需要csrf
			.csrf().disable()
			// 基于token,不需要session
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			// 设置myUnauthorizedHandler处理认证失败、鉴权失败
			.exceptionHandling().authenticationEntryPoint(jwtAuthError).accessDeniedHandler(jwtAuthError).and()
			// 开始设置权限
			.authorizeRequests()
			//需要登录
			.antMatchers("/hello").authenticated()
			//除了上面外的请求全部不拦截
			.anyRequest().permitAll();

		// 添加JWT过滤器，JWT过滤器在用户名密码认证过滤器之前
		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	}

	// 配置跨源访问(CORS)
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

}
