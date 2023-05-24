package com.cos.jwt.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.domain.Manager;
import com.cos.jwt.repository.ManagerRepository;



//시큐리티가 filter가지고 있는데 그 필터중에 BasicAuthenticationfilter 라는 것이 있음.
//권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있음.
//만약에 권한이 인증이 필요한 주소가 아니라면 이 필터를 안탐
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	
	private ManagerRepository managerRepo;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, ManagerRepository managerRepo) {
		super(authenticationManager);
		this.managerRepo = managerRepo;
	}
	//인증이나 권한이 필요한 주소요청이 있을 대 해당 필터를 타게됨
		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
				throws IOException, ServletException {
			//뒤쪽에도 있어서 응답이 두번나서 오류 지워야함
			//super.doFilterInternal(request, response, chain);
			
			System.out.println("인증이나 권한이 필요한 주소 요청이 됨.");
			
			String jwtHeader = request.getHeader("Authorization");
			System.out.println("jwtHeader:"+ jwtHeader);
			
			//header가 있는지 확인
			if(jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
				chain.doFilter(request, response);
				return;
			}//JWT 토큰을 검증을 해서 정상적인 사용자인지 확인
			String jwtToken = request.getHeader("Authorization").replace("Bearer ","");
			
			String managerid = JWT.require(Algorithm.HMAC512("jwt")).build().verify(jwtToken).getClaim("managerid").asString();
			//서명이 정상적으로 됨 , 사용자가 인증됐음
			if(managerid != null) {
				Manager managerEntity = managerRepo.findByManagerid(managerid);
				
				PrincipalDetails principalDetails = new PrincipalDetails(managerEntity);
				//Authentication객체를 강제로 만들기 
				//Jwt토큰 서명을 통해서 서명이 정상이면 Authentication객체를 만들어준다..
				Authentication authentication = 
						new UsernamePasswordAuthenticationToken(principalDetails, null,principalDetails.getAuthorities());
				
				//강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				chain.doFilter(request, response);
			}
		}
}
