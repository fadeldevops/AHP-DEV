package com.example.learn.api.master.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.learn.api.master.base.controller.BaseController;
import com.example.learn.api.master.base.response.Response;
import com.example.learn.api.master.base.response.SearchResponse;
import com.example.learn.api.master.entity.CarSumMatriks;
import com.example.learn.api.master.exception.ValidationException;
import com.example.learn.api.master.request.CarRequest;
import com.example.learn.api.master.request.SearchCarReq;
import com.example.learn.api.master.response.CarResponse;
import com.example.learn.api.master.service.CarService;
import com.example.learn.api.master.service.CarComparisonMatriksService;
import com.example.learn.api.master.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("car")
public class CarController extends BaseController {

   @Autowired
   CarService service;

   @Autowired
   CarComparisonMatriksService serviceComparison;
   @Autowired
   private ResponseUtil responseUtil;

   @PostMapping("/post")
   public Response<CarResponse> create(Authentication authentication, @RequestBody @Valid CarRequest req) {

      CarResponse respon = service.post(req, this.getLoginUsername(authentication));

      return responseUtil.generateResponseSuccess(respon);

   }

   @GetMapping("/get/{id}")
   public Response<CarResponse> get(Authentication authentication, @PathVariable String id) {
      CarResponse response = service.get(id);

      return responseUtil.generateResponseSuccess(response);
   }

   @PutMapping("/put/{id}")
   public Response<CarResponse> update(Authentication authentication, @RequestBody @Valid CarRequest req,
         @PathVariable String id) {

      if (!id.equalsIgnoreCase(req.getCarCd())) {
         throw new ValidationException("COMMNERR00007", "Car Code", id, req.getCarCd());
      }
      CarResponse respon = service.put(req, this.getLoginUsername(authentication));

      return responseUtil.generateResponseSuccess(respon);

   }

   @SuppressWarnings("rawtypes")
   @GetMapping("/getAll")
   public Response<SearchResponse> search(Authentication authentication, @Valid SearchCarReq request) {
      SearchResponse response = service.search(request);

      return responseUtil.generateResponseSuccess(response);
   }

   @GetMapping("/getAllComparison")
   public Response<List<CarSumMatriks>> getAllComparison(Authentication authentication) {
      List<CarSumMatriks> response = serviceComparison.getAllComparison();
      return responseUtil.generateResponseSuccess(response);
   }

}
