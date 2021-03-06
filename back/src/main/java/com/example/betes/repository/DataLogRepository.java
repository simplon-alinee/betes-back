package com.example.betes.repository;

import com.example.betes.model.DataLog;
import com.example.betes.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;

@Repository
public interface DataLogRepository extends PagingAndSortingRepository<DataLog, Long > {

	DataLog findFirstByGoalOrderByIdDesc(String goal);

	DataLog findFirstByGoalAndConcernedDataOrderByIdDesc(String goal, String concernedData);
}
