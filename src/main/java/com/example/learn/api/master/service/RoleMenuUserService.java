package com.example.learn.api.master.service;

import com.example.learn.api.master.base.service.BaseService;
import com.example.learn.api.master.request.RoleRequest;
import com.example.learn.api.master.response.DeletedCommonResp;
import com.example.learn.api.master.vo.RoleMenuUserVo;

public interface RoleMenuUserService extends BaseService {

   RoleMenuUserVo create(RoleRequest req, String username);

   RoleMenuUserVo update(RoleRequest req, String username);

   RoleMenuUserVo get(String roleCd);

   DeletedCommonResp delet(String roleCd);

}
