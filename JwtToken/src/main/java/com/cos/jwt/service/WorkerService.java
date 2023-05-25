package com.cos.jwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.jwt.domain.Worker;
import com.cos.jwt.repository.WorkerRepository;

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
	public void workerdelete(Worker worker) {
		workerRepo.deleteByuserCode(worker.getUserCode());
		
	}
}
