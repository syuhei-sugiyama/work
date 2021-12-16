package com.example.demo.util.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.util.model.Saiban;

public interface SaibanRepository extends JpaRepository<Saiban, String> {

	Saiban findBySaibanId(String saibanKey);
}
