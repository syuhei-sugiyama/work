package com.example.demo.waitingList.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.waitingList.model.WaitingListHistory;

public interface WaitingListHistoryRepository extends JpaRepository<WaitingListHistory, String> {

	WaitingListHistory findByWaitingListHistoryId(String waitingListHistoryId);

}
