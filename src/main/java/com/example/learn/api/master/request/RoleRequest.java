package com.example.learn.api.master.request;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {

   private String roleCd;
   private String roleName;
   private Boolean adminFlag;
   private String username;
   List<RoleMenuReq> listRoleMenu = new ArrayList<>();

}
