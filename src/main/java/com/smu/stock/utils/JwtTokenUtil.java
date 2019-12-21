package com.smu.stock.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smu.stock.entity.AuthUser;
import com.smu.stock.entity.User;
import com.smu.stock.utils.redis.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtTokenUtil implements Serializable {
	//不进行序列化
	private transient Clock clock = DefaultClock.INSTANCE;

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	private ObjectMapper mapper = new ObjectMapper();

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}


	public AuthUser getAuthUserFromToken(String token) throws Exception{
		String subject = getClaimFromToken(token, Claims::getSubject);
		Map<String,Object> subjectMap = mapper.readValue(subject, Map.class);
		AuthUser authUser = new AuthUser();
		User user =new User();
		user.setUid(subjectMap.get("userId").toString());
		user.setUserName(subjectMap.get("userName").toString());
		user.setPassword(subjectMap.get("password").toString());
		user.setStatus(subjectMap.get("status").toString());
		authUser.setUser(user);
		return authUser;
	}
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public <T> T getClaimFromToken(String token, String claimName, Class<T> requiredType) {
		final Claims claims = getAllClaimsFromToken(token);
		return claims.get(claimName, requiredType);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}

	/**
	 * 获取颁发时间
	 * @param token
	 * @return
	 */
	public Date getIssuedAtDateFromToken(String token){
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	/**
	 * 获取期满时间
	 * @param token
	 * @return
	 */
	public Date getExpirationDateFromToken(String token){
		return getClaimFromToken(token,Claims::getExpiration);
	}

	/**
	 * 获取token是否过期
	 * @param token
	 * @return
	 */
	private boolean isTokenExpired(String token){
		Date expirationDate = getExpirationDateFromToken(token);
		return expirationDate.before(clock.now());
	}

	/**
	 * 创建时间是否在最后更新密码时间
	 * @param created
	 * @param lastPasswordReset
	 * @return
	 */
	private boolean isCreateBeforeLastPasswordRest(Date created, Date lastPasswordReset){
		return (lastPasswordReset !=null && created.before(lastPasswordReset));
	}

	/**
	 * 生成token
	 * @param user
	 * @return 登陆校验成功后调用接口生成token
	 */
	public String generateToken(User user){
		Map<String,Object> claims = new HashMap<>();
		claims.put("role", user.getRole());
		String token = doGenerateToken(claims, user.getMobile());
		log.info("generateToken subject:{}", user.getMobile());
		return token;
	}

	/**
	 * 生成token
	 * @param claims
	 * @param subject
	 * @return
	 */
	private String doGenerateToken(Map<String,Object> claims, String subject) {
		final Date createdDate = clock.now();
		final Date expirationDate = calculateExpirationDate(createdDate);
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(createdDate)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	/**
	 * 计算token过期时间
	 * @param createdDate
	 * @return
	 */
	private Date calculateExpirationDate(Date createdDate) {
		//过期时间1天
		return new Date(createdDate.getTime() + 1000 * 60 * 60 * 24);
	}

	/**
	 * 校验token是否过期
	 * @param token
	 * @param userDetails
	 * @return
	 * @throws Exception
	 */
	public boolean validateToken(String token,UserDetails userDetails) throws Exception {
		String username = userDetails.getUsername();
		final AuthUser authUser = getAuthUserFromToken(token);
		return (
				authUser.getUsername().equals(username)
						&& !isTokenExpired(token));
	}

	/**
	 * 刷新token
	 * @param token
	 * @return
	 */
	public String refreshToken(String token) {
		final Date createdDate = clock.now();
		final Date expirationDate = calculateExpirationDate(createdDate);
		final Claims claims = getAllClaimsFromToken(token);
		claims.setIssuedAt(createdDate);
		claims.setExpiration(expirationDate);
		return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
}
