package com.example.betes.service;

import com.example.betes.model.DataLog;
import com.example.betes.repository.DataLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
	public DataLog findFirstByGoalAndConcernedDataOrderByIdDesc(String goal, String concernedData) {
		return this.dataLogRepository.findFirstByGoalAndConcernedDataOrderByIdDesc(goal,concernedData);
	}
	public Boolean needInit() {
		Long count = this.dataLogRepository.count();
		return count == 0;
	}

}
