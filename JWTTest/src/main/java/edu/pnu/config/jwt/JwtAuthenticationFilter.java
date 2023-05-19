package edu.pnu.config.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.config.auth.PrincipalDetails;
import edu.pnu.model.User;
import lombok.RequiredArgsConstructor;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음
// login 요청해서 username,password 전송하면 (post)
//UsernamePasswordAuthenticationFilter 동작을 함

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private final AuthenticationManager authenticationManager;

	// login 요청을 하면 로그인 시도를 위해서 실행되는 함수
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter 로그인 시도");
		try {
			//1.username, password 받아서 attemptAuthentication로 로그인 시도
			ObjectMapper om = new ObjectMapper(); //json을 파싱해줌
			User user = om.readValue(request.getInputStream(), User.class);
			System.out.println(user);
			// 토큰 만들기 
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			//2.PrincipalDetailsService의 loadUserByUsername()함수가 실행된후 정상이면 authentication이 리턴됨
			//DB에 있는 username와 password가 일치하면 만들어진다
			//authentication 로그인 정보가 담김
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			// ->로그인이 되었다.
			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();//오브젝트로 리턴하기때문에 다운케스팅
			System.out.println("로그인 완료됨"+principalDetails.getUser().getUsername()); // 로그인 정석적으로 되었다는뜻
			//3.authentication 객체가 session영역에 저장을 해야하고 그방법이 return 해주면 됨
			//리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고 하는거임
			//굳이 JWT토큰을 사용하면서 세션을 만들이유가 없음. 근데 단지 권한 처리때문에 session 넣어 준다.
			
			return authentication;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	// attemptAuthentication 실행후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행
	// 4.JWT토큰을 만들어서 request요청한 사용자에게 JWT토큰을 response 응답해주면 됨
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication실행됨: 인증이 완료 되었다는 뜻");
		//jwt토큰 만들기
		PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();//오브젝트로 리턴하기때문에 다운케스팅
		//RSA방식은 아니구 Hash 암호 방식
		String jwtToken = JWT.create()
				//토큰이름
				.withSubject("cos토큰")
				//토큰 만료시간 60000 == 1분
				.withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
				//비공개 클래임
				.withClaim("id", principalDetails.getUser().getId())
				.withClaim("username", principalDetails.getUser().getUsername())
				//서버만 아는 고유한 값으로 해야함
				.sign(Algorithm.HMAC512("cos"));
		// Authorization가 해더에 담겨서 사용자에 응답
		response.addHeader("Authorization","Bearer "+ jwtToken);
	}
	
}
