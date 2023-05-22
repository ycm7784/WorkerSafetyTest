package com.cos.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.jwt.domain.Manager;
import com.cos.jwt.repository.ManagerRepository;

@Service
public class ManagerService {
	
	@Autowired
	ManagerRepository managerRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public Manager joinManager (Manager manager) {
		manager.setPassword(bCryptPasswordEncoder.encode(manager.getPassword()));
		return managerRepo.save(manager);
		
	}
}
