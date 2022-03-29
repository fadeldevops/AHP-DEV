package com.example.learn.api.master.repository;

import java.util.List;

import com.example.learn.api.master.vo.RoleVo;

public interface RoleDAO {

   List<RoleVo> getActiveRoles(String username);

}
