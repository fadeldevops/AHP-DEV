package com.example.learn.api.master.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.learn.api.master.base.service.impl.BaseServiceImpl;
import com.example.learn.api.master.repository.RoleDAO;
import com.example.learn.api.master.service.RoleService;
import com.example.learn.api.master.vo.AuthInfoVO;
import com.example.learn.api.master.vo.RoleVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

   @Autowired
   RoleDAO roleDAO;

   @Override
   public AuthInfoVO getAuthorizationInfo(String username) {

      List<RoleVo> getActivesRole = roleDAO.getActiveRoles(username);
      List<String> roleCds = getActivesRole.stream().map(roleVo -> roleVo.getRoleCd()).collect(Collectors.toList());
      Boolean isAdminFLag = getActivesRole.stream().anyMatch(roleVo -> roleVo.getAdminFlag());

      return new AuthInfoVO(roleCds, isAdminFLag);
   }

   @Override
   public Boolean isAdminFlag(String username) {
      List<RoleVo> getActivesRole = roleDAO.getActiveRoles(username);
      return getActivesRole.stream().anyMatch(roleVo -> roleVo.getAdminFlag());
   }

}
