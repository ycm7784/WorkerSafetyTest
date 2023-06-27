package com.cos.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.api.domain.Worker;
import com.cos.api.repository.WorkerRepository;

@Service
public class WorkerService {
	
	@Autowired
	WorkerRepository workerRepo;
	
	
	public List<Worker> workerList() {
		List<Worker> worker  = workerRepo.findAll();
		return worker;
	}
	
	public void workeradd(Worker worker) {
		workerRepo.save(worker);
	}
	@Transactional
	public void workerdelete(int code) {
		workerRepo.deleteByuserCode(code);
		
	}
	public  Optional<Worker> workerid(int i) {
		return workerRepo.findById(i);
	}
}
