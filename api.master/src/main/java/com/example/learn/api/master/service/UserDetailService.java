package com.example.learn.api.master.service;

import com.example.learn.api.master.vo.UserVo;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailService {

   UserDetails loadUserByUsername(String username);

   UserVo getPass(String username);

}
