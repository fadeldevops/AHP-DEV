package com.example.learn.api.master.service;

import com.example.learn.api.master.base.service.BaseService;
import com.example.learn.api.master.request.CarRequest;
import com.example.learn.api.master.response.CarResponse;

public interface CarService extends BaseService {

   CarResponse post(CarRequest req, String username);

   CarResponse get(String carCd);

}
