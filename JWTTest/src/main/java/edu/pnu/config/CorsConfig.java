package edu.pnu.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration // 스프링에서 안열릴수 있게
public class CorsConfig {
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); // 내서버가 응답을 할때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것
		config.addAllowedOrigin("*"); // 모든 ip에 응답을 허용하겠다
		config.addAllowedHeader("*"); // 모든 header에 응답을 허용하겠다.
		config.addAllowedMethod("*"); // 모든 post,get, delete, patch 요청을 허용하겠다.
		source.registerCorsConfiguration("/api/**", config);///api/**에 들어오는 모든주소는 config의 설정을 따라 하라
		return new CorsFilter(source);
	}
}
