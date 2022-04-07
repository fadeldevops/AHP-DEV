package com.example.learn.api.master.repository;

import java.util.List;

import com.example.learn.api.master.entity.CarSumMatriks;

public interface CarComparisonDAO {

   void saveComparison(CarSumMatriks req, String username);

   void saveSum(CarSumMatriks req, String username);

   void deletCarType(String carType);

   List<CarSumMatriks> getAllCOmparisonValueMatriks();

}
