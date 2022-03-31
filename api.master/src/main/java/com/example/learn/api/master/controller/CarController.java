package com.example.learn.api.master.controller;

import javax.validation.Valid;

import com.example.learn.api.master.base.controller.BaseController;
import com.example.learn.api.master.base.response.Response;
import com.example.learn.api.master.request.CarRequest;
import com.example.learn.api.master.response.CarResponse;
import com.example.learn.api.master.service.CarService;
import com.example.learn.api.master.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("car")
public class CarController extends BaseController {

   @Autowired
   CarService service;
   @Autowired
   private ResponseUtil responseUtil;

   @PostMapping("/car")
   public Response<CarResponse> create(Authentication authentication, @RequestBody @Valid CarRequest req) {

      CarResponse respon = service.post(req, this.getLoginUsername(authentication));

      return responseUtil.generateResponseSuccess(respon);

   }

   @GetMapping("/{id}")
   public Response<CarResponse> get(Authentication authentication, @PathVariable String id) {
      CarResponse response = service.get(id);

      return responseUtil.generateResponseSuccess(response);
   }

}
