package com.example.learn.api.master.repository;

import com.example.learn.api.master.entity.UserProfileEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserProfileRepo extends JpaRepository<UserProfileEntity, String> {

      @Transactional
      @Modifying
      @Query(value = " DELETE FROM tb_m_user_profile c " +
                  " WHERE c.username = :username ", nativeQuery = true)
      void deletUserProfile(@Param("username") String username);

}
