package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.model.User;
import edu.pnu.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
public class RestApiController {
	
	@Autowired
	private UserRepository userRepository ;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	
	@GetMapping("home")
	public String home() {
		return "<h1>home</h1>";
	}
	
	@PostMapping("token")
	public String token() {
		return "<h1>token</h1>";
	}
	
	@PostMapping("join")
	public String join(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles("ROLE_USER");
		userRepository.save(user);
		return "회원가입완료";
	}
	//user,manager,admin 접근가능
	@GetMapping("/api/v1/user")
	public String user() {
		return "user";
	}
	//manager,admin 접근가능
	@GetMapping("/api/v1/manager")
	public String manager() {
		return "manager";
	}
	//admin 접근가능
	@GetMapping("/api/v1/admin")
	public String admin() {
		return "admin";
	}
}
