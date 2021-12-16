package com.example.demo.hairdresser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.hairdresser.model.Hairdresser;

public interface HairdresserRepository extends JpaRepository<Hairdresser, String> {

	List<Hairdresser> findByHairdresserName(String hairdresserName);

	Hairdresser findByHairdresserId(String hairdresserId);
}
