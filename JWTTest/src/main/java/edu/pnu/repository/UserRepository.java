package edu.pnu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
