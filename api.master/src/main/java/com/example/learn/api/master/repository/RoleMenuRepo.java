package com.example.learn.api.master.repository;

import com.example.learn.api.master.entity.RoleMenuEntity;
import com.example.learn.api.master.entity.RoleMenuId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleMenuRepo extends JpaRepository<RoleMenuEntity, RoleMenuId> {

}
