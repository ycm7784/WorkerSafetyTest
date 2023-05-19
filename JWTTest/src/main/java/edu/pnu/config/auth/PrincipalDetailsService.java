package edu.pnu.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.pnu.model.User;
import edu.pnu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//http://localhost:8080/login
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService  {
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService : 진입"+username);
		User user = userRepository.findByUsername(username);
		
		System.out.println("userEntity:"+ user);
		// session.setAttribute("loginUser", user);
		return new PrincipalDetails(user);
	}
}
