package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.MsgData;

public interface MsgDataRepository extends JpaRepository<MsgData, Long> {

}
