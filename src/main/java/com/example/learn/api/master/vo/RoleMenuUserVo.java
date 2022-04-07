package com.example.learn.api.master.vo;

import java.util.List;

import com.example.learn.api.master.request.RoleMenuReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleMenuUserVo {

   private String roleCd;
   private String roleName;
   private Boolean adminFlag;
   private String username;
   private List<RoleMenuReq> listRoleMenu;

}
