package com.example.demo.hairdresser.serviceif;

import java.util.Map;

import com.example.demo.hairdresser.model.Hairdresser;

public interface HairdresserService {

	void addHairdresser(Hairdresser hairdresserForm);

	void updateHairdresser(Hairdresser hairdresserForm);

	void deleteHairdresser(String hairdresserId);

	Map<String, String> getAllHairdresserMap();
}
