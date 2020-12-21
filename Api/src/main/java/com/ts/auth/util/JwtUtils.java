package com.ts.auth.util;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.auth.interceptor.AuthUser.Role;
import com.ts.auth.properties.JwtConstant;
import com.ts.auth.properties.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * jwt-token的工具类：包括token生成、验证、加密
 * 
 * @author dy 2020年12月16日
 */
@Component
public class JwtUtils {

	@Autowired
	private JwtProperties jwtProperties;
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * 签发JWT
	 * 
	 * @param username
	 * @param subject  可以是JSON数据 尽可能少
	 * @return String
	 *
	 */
	public TokenResult createJWT(String username, String subject) {
		long nowMillis = System.currentTimeMillis();
		 String token = Jwts.builder().setId(username).setSubject(subject) // 主题
				.setIssuer("user") // 签发者
				.setIssuedAt(new Date(nowMillis)) // 签发时间
				.signWith(SignatureAlgorithm.HS256, generalKey()) // 签名算法以及密匙
				.setExpiration(new Date(nowMillis + jwtProperties.getTOKEN_EXP_TIME())) // 过期时间
				.compact();
		TokenResult tokenResult = new TokenResult();
		tokenResult.setAccess_token(token);
		tokenResult.setExpires_in(jwtProperties.getTOKEN_EXP_TIME()/1000-1);
		return tokenResult;
	}

	public static SecretKey generalKey() {
		byte[] encodedKey = JwtConstant.JWT_KEY.getBytes();
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}

	/**
	 * 
	 * 解析JWT字符串
	 * 
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) {
		SecretKey secretKey = generalKey();
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
	}
	
	/**
	 * 从 http 请求头中取出 token getToken
	 * 
	 * @param request
	 * @return
	 * @author dy 2020年12月16日
	 */
	public static String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");// 先从header取token
		if (StringUtils.isNotBlank(token)) {
			token = token.substring(token.indexOf(" ")+1);
		}else {// 如果token为null，再从请求参数中取
			token = request.getParameter(JwtConstant.ACCESS_TOKEN);
		}
		return token;
	}
	
	/**
	 * 获取当前登录用户角色
	 * @return
	 */
	public Role getRole() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();
		String token = getToken(request);
		Claims claims = JwtUtils.parseJWT(token);
		return objectMapper.convertValue(claims.getSubject(), Role.class);
	}
}
