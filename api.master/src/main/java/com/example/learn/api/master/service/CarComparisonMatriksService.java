package com.example.learn.api.master.service;

import java.util.List;

import com.example.learn.api.master.base.service.BaseService;
import com.example.learn.api.master.entity.CarSumMatriks;

public interface CarComparisonMatriksService extends BaseService {

   void insertValue(String username);

   List<CarSumMatriks> getAllComparison();

}
