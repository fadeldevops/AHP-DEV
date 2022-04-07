package com.example.learn.api.master.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.example.learn.api.master.base.controller.BaseController;
import com.example.learn.api.master.base.response.Response;
import com.example.learn.api.master.exception.ValidationException;
import com.example.learn.api.master.request.UserRequest;
import com.example.learn.api.master.response.DeletedCommonResp;
import com.example.learn.api.master.response.UserResponse;
import com.example.learn.api.master.service.UserService;
import com.example.learn.api.master.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {

   @Autowired
   UserService service;

   @Autowired
   private ResponseUtil responseUtil;

   @PostMapping("/create-user")
   public Response<UserResponse> create(Authentication authentication, @RequestBody @Valid UserRequest req) {

      UserResponse respon = service.create(req, this.getLoginUsername(authentication));

      return responseUtil.generateResponseSuccess(respon);

   }

   @GetMapping("/{username}")
   public Response<UserResponse> get(Authentication authentication, @PathVariable @NotBlank String username) {
      UserResponse response = service.get(username);

      return responseUtil.generateResponseSuccess(response);
   }

   @DeleteMapping("/{username}")
   public Response<DeletedCommonResp> delet(Authentication authentication, @PathVariable @NotBlank String username) {
      DeletedCommonResp response = service.delet(username);

      return responseUtil.generateResponseSuccess(response);
   }

   @PutMapping("/{id}")
   public Response<UserResponse> update(Authentication authentication, @PathVariable @NotBlank String id,
         @RequestBody @Valid UserRequest req) {

      if (!id.equals(req.getUsername())) {
         throw new ValidationException("COMMNERR00007", "Message", id, req.getUsername());
      }

      UserResponse responApi = service.update(req, this.getLoginUsername(authentication));

      return responseUtil.generateResponseSuccess(responApi);

   }

}
