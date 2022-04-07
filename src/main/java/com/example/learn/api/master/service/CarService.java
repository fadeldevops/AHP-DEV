package com.example.learn.api.master.service;

import com.example.learn.api.master.base.response.SearchResponse;
import com.example.learn.api.master.base.service.BaseService;
import com.example.learn.api.master.entity.CarEntity;
import com.example.learn.api.master.request.CarRequest;
import com.example.learn.api.master.request.SearchCarReq;
import com.example.learn.api.master.response.CarResponse;

public interface CarService extends BaseService {

   CarResponse post(CarRequest req, String username);

   CarResponse get(String carCd);

   CarResponse put(CarRequest req, String username);

   SearchResponse<CarEntity> search(SearchCarReq req);

}
