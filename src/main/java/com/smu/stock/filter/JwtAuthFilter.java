package com.smu.stock.filter;

import com.smu.stock.auth.FilterIgnorePropertiesConfig;
import com.smu.stock.entity.AuthUser;
import com.smu.stock.auth.JwtTokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

	private OrRequestMatcher orRequestMatcher;

	private String tokenHeader = "Authorization";

	private String tokenPrefix = "Bearer";

	// 初始化忽略的url不走过此滤器
	@PostConstruct
	public void init() {
		List<RequestMatcher> matchers = filterIgnorePropertiesConfig.getUrls().stream()
				.map(url -> new AntPathRequestMatcher(url))
				.collect(Collectors.toList());
		orRequestMatcher = new OrRequestMatcher(matchers);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String authHeader = request.getHeader(this.tokenHeader);
		// 从http头部读取jwt
		if(authHeader != null && authHeader.startsWith(tokenPrefix)){
			String authToken = authHeader.substring(tokenPrefix.length() + 1);
			String userName = null , role = null;
			try {
				userName = jwtTokenUtil.getUsernameFromToken(authToken);
				role = jwtTokenUtil.getClaimFromToken(authToken, "role", String.class);
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("异常信息",e);
			}
			// 如果jwt正确解出账号信息，说明是合法用户，设置认证信息，认证通过
			if(!StringUtils.isEmpty(userName) &&
					SecurityContextHolder.getContext().getAuthentication() == null){
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
						userName, null, AuthUser.getAuthoritiesByRole(role));

				// 把请求的信息设置到UsernamePasswordAuthenticationToken details对象里面，包括发请求的ip等
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// 设置认证信息
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		// 调用下一个过滤器
		filterChain.doFilter(request,response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return orRequestMatcher.matches(request);

	}
}
