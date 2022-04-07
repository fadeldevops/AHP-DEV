package com.example.learn.api.master.controller;

import javax.validation.Valid;

import com.example.learn.api.master.base.controller.BaseController;
import com.example.learn.api.master.base.response.Response;
import com.example.learn.api.master.request.RoleRequest;
import com.example.learn.api.master.service.RoleMenuUserService;
import com.example.learn.api.master.util.ResponseUtil;
import com.example.learn.api.master.vo.RoleMenuUserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("role-menu-user")
public class RoleController extends BaseController {

   @Autowired
   RoleMenuUserService service;

   @Autowired
   ResponseUtil responseUtil;

   @PostMapping("/create")
   public Response<RoleMenuUserVo> create(Authentication authentication, @RequestBody @Valid RoleRequest req) {

      RoleMenuUserVo respon = service.create(req, this.getLoginUsername(authentication));

      return responseUtil.generateResponseSuccess(respon);

   }

}
