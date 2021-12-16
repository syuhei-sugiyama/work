package com.example.demo.yoyaku.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.yoyaku.model.YoyakuRireki;

public interface YoyakuRirekiRepository extends JpaRepository<YoyakuRireki, String> {

	YoyakuRireki findByYoyakuRirekiId(String yoyakuRirekiId);
}
