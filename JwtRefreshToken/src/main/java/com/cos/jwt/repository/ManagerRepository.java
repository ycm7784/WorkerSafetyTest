package com.cos.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.jwt.domain.Manager;

public interface ManagerRepository extends JpaRepository<Manager, String> {
	
	public Manager findByManagerid(String managerid);

}
