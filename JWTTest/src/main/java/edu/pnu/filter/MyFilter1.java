package edu.pnu.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFilter1 implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request; //요청
		HttpServletResponse res = (HttpServletResponse) response; // 응답
		
		// 토큰: cos이걸 만들어줘야함. id,pw정상적으로 들어와서 로그인이 완료 되면 토큰을 만들어주고 그걸 응답을 해준다.
		// 요청할 때 마다 header에 Authorization에 value값으로 토큰을 가지고 옴
		// 그때 토큰이 넘어오면 이 토큰이 내가 만든 토큰이 맞는지만 검증만 하면 됨(RSA, HS256)
		if(req.getMethod().equals("POST")) {
			System.out.println("POST 요청됨");
			String headerAuth = req.getHeader("Authorization"); // req의 해더에서 Authorization를 가져옴
			System.out.println(headerAuth);
			System.out.println("필터1");
			if(headerAuth.equals("cos")) {
				chain.doFilter(req, res);
			}else {
				PrintWriter out = res.getWriter();
				out.println("인증안됨");
			}
		}
		
		
		
		
	}
	
}
