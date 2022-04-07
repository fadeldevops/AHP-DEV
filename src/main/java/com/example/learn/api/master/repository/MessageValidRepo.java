package com.example.learn.api.master.repository;

import com.example.learn.api.master.entity.MessageValidEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageValidRepo extends JpaRepository<MessageValidEntity, String> {

}
