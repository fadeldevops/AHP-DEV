package com.example.learn.api.master.controller;

import javax.validation.Valid;

import com.example.learn.api.master.base.response.Response;
import com.example.learn.api.master.request.EmployeeRequest;
import com.example.learn.api.master.response.EmployeResponse;
import com.example.learn.api.master.response.LoginResponse;
import com.example.learn.api.master.service.EmployeeService;
import com.example.learn.api.master.service.LoginService;
import com.example.learn.api.master.util.ResponseUtil;
import com.example.learn.api.master.vo.User;
import com.example.learn.api.master.vo.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

   @Autowired
   LoginService service;

   @Autowired
   private ResponseUtil responseUtil;

   @Autowired
   private EmployeeService serviceEmp;

   @PostMapping("/login")
   public Response<LoginResponse> login(@RequestBody @Valid User user) {

      LoginResponse loginResponse = service.login(user.getUsername(), user.getPassword());

      return responseUtil.generateResponseSuccess(loginResponse);
   }

   @PostMapping("/create")
   public Response<EmployeResponse> create(Authentication authentication, @RequestBody @Valid EmployeeRequest req) {

      EmployeResponse respon = serviceEmp.createEmployee(req, this.getLoginUsername(authentication));

      return responseUtil.generateResponseSuccess(respon);

   }

   protected UserDetails getLoginUserInfo(Authentication authentication) {

      return (UserDetails) authentication.getPrincipal();
   }

   protected String getLoginUsername(Authentication authentication) {

      return this.getLoginUserInfo(authentication).getUsername();
   }

}
