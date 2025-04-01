package com.min.edu.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.min.edu.service.CustomerDetailServiceImpl;
import com.min.edu.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 1) Client로부터 요청된 token을 받는다.
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		// 2) JWT token을 Client 요청의 Header에서로부터 추출한다.
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			// 3) token으로부터 JWTService의 사용자 이름을 추출한다.
			username = jwtService.extractUserName(token);
		}
		// 4) JWT에서로부터 사용자 이름을 추출하고, 현재 인증정보가 없으면 사용자 정보를 조회한다.
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = applicationContext
										.getBean(CustomerDetailServiceImpl.class)
										.loadUserByUsername(username);
		// 5) JWT가 유효한지 확인한 후, 유효한 경우에는 인증객체를 생성하고 Spring Security Context에 저장한다.
			// 5-1) 토큰 검증하기(토큰만료 및 토큰이 해당 사용자에 대한 것이 맞는지에 대한 검증)
			if(jwtService.validationToken(token, userDetails)) {
				// 5-2) 사용자 권한 확인 정보와 정보가 원하지 않는 자격증명은 null이고 원하는 권한은 전달한다.
				UsernamePasswordAuthenticationToken authToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
