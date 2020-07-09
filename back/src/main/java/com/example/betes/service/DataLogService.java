package com.example.betes.service;

import com.example.betes.model.DataLog;
import com.example.betes.repository.DataLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DataLogService {

	@Autowired
	DataLogRepository dataLogRepository;

	public DataLogService() {
	}

	public void saveDataLog(DataLog dataLog) {
		this.dataLogRepository.save(dataLog);
	}

	public DataLog findFirstByGoalOrderByIdDesc(String goal) {
		return this.dataLogRepository.findFirstByGoalOrderByIdDesc(goal);
	}

}
