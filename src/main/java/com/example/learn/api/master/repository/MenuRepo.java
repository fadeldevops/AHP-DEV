package com.example.learn.api.master.repository;

import com.example.learn.api.master.entity.MenuEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepo extends JpaRepository<MenuEntity, String> {

}
