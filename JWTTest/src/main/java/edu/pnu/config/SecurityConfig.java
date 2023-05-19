package edu.pnu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import edu.pnu.config.jwt.JwtAuthenticationFilter;
import edu.pnu.config.jwt.JwtAuthorizationFilter;
import edu.pnu.repository.UserRepository;

@Configuration
@EnableWebSecurity// 시큐리티 활성화 -> 기본 스프링 필터체인에 등록

public class SecurityConfig   {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CorsConfig corsConfig;
	@Bean
	public BCryptPasswordEncoder paswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		//http.addFilterBefore(new MyFilter1(),BasicAuthenticationFilter.class);
//		
//		http.csrf().disable();// CSRF 보호를 비활성화하고 CORS비활성화
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// Session을 사용하지 않는다
//		.and()
//		.formLogin().disable()// formLogin을 쓰지 않는다.
//		.httpBasic().disable()// httpBasic을 쓰지 않는다.
//		.apply(new MyCustomDsl())
//		.and()
//		.authorizeHttpRequests() //메서드를 사용하여 요청에 대한 권한 설정 
//		.antMatchers("api/v1/user/**").hasAnyRole("USER","MANAGER","USER")// ROLE_USER,ROLE_MANAGER,ROLE_ADMIN 권한을 가진 사용자만 접근 허용
//		//.hasRole("USER")  .access(anyOf(hasRole("ROLE_USER"), anyOf(hasRole("ROLE_MANAGER") , anyOf(hasRole("ROLE_USER"))// ROLE_USER,ROLE_MANAGER,ROLE_ADMIN만 허용
//		.antMatchers("api/v1/manager/**").hasAnyRole("MANAGER","ADMIN")// 들어오는 요청에 대해 인증 필요
//		//.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//		.antMatchers("api/v1/admin/**").hasRole("ADMIN")
//		.anyRequest().permitAll(); // 다른쪽 접근은 배제 
//		return http.build();
//		
//	}
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.formLogin().disable()
				.httpBasic().disable()
				.apply(new MyCustomDsl()) // 커스텀 필터 등록
				.and()
				.authorizeRequests(authroize -> authroize.antMatchers("/api/v1/user/**")
						.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
						.antMatchers("/api/v1/manager/**")
						.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
						.antMatchers("/api/v1/admin/**")
						.access("hasRole('ROLE_ADMIN')")
						.anyRequest().permitAll())
				.build();
	}
	public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
			http
					.addFilter(corsConfig.corsFilter())// @CrossOrigin(인증x),시큐리티 필터에 등록 인증(ㅇ)
					.addFilter(new JwtAuthenticationFilter(authenticationManager))// AuthenticationManger
					.addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
		}
	}
}
