package com.example.learn.api.master.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.learn.api.master.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, String> {

      @Query(value = "select username, pass from public.tb_m_user " +
                  " where username = :username ", nativeQuery = true)
      List<Object[]> getPass(@Param("username") String username);

      @Modifying
      @Transactional
      @Query(value = " DELETE FROM tb_m_user c " +
                  " WHERE c.username = :username ", nativeQuery = true)
      void deletUser(@Param("username") String username);

}
