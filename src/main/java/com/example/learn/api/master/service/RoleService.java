package com.example.learn.api.master.service;

import com.example.learn.api.master.base.service.BaseService;
import com.example.learn.api.master.vo.AuthInfoVO;

public interface RoleService extends BaseService {
   AuthInfoVO getAuthorizationInfo(String username);

   Boolean isAdminFlag(String username);
}
