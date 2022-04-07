package com.example.learn.api.master.controller;

import javax.validation.Valid;

import com.example.learn.api.master.base.controller.BaseController;
import com.example.learn.api.master.base.response.Response;
import com.example.learn.api.master.request.EmployeeRequest;
import com.example.learn.api.master.response.EmployeResponse;
import com.example.learn.api.master.service.EmployeeService;
import com.example.learn.api.master.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("emp")
public class EmployeeController extends BaseController {

   @Autowired
   private EmployeeService service;

   @Autowired
   private ResponseUtil responseUtil;

   @PostMapping("/create-emp")
   public Response<EmployeResponse> create(Authentication authentication, @RequestBody @Valid EmployeeRequest req) {

      EmployeResponse respon = service.createEmployee(req, this.getLoginUsername(authentication));

      return responseUtil.generateResponseSuccess(respon);

   }

   @GetMapping("/{id}")
   public Response<EmployeResponse> get(Authentication authentication, @PathVariable @NotBlank String id) {
      EmployeResponse response = service.get(id);

      return responseUtil.generateResponseSuccess(response);
   }

}