package com.example.learn.api.master.repository;

import com.example.learn.api.master.entity.RoleUserEntity;
import com.example.learn.api.master.entity.RoleUserId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleUserRepository extends JpaRepository<RoleUserEntity, RoleUserId> {

}
