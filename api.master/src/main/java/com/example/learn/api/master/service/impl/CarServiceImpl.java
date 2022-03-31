package com.example.learn.api.master.service.impl;

import com.example.learn.api.master.base.service.impl.BaseServiceImpl;
import com.example.learn.api.master.entity.CarComparisonEntity;
import com.example.learn.api.master.entity.CarEntity;
import com.example.learn.api.master.exception.ValidationException;
import com.example.learn.api.master.repository.CarComparisonRepo;
import com.example.learn.api.master.repository.CarRepo;
import com.example.learn.api.master.request.CarComparisonReq;
import com.example.learn.api.master.request.CarRequest;
import com.example.learn.api.master.response.CarResponse;
import com.example.learn.api.master.service.CarService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class CarServiceImpl extends BaseServiceImpl implements CarService {

   @Autowired
   CarRepo repoCar;

   @Autowired
   CarComparisonRepo repoCarComparison;

   private Boolean validation(CarRequest req) {

      Boolean valid = false;

      if (StringUtils.isEmpty(req.getCarCd())) {
         throw new ValidationException("messageCode", "Car Code");
      } else {
         valid = true;
      }

      if (StringUtils.isEmpty(req.getCarName())) {
         throw new ValidationException("messageCode", "Car Name");
      } else {
         valid = true;
      }

      if (req.getCarYear() == null) {
         throw new ValidationException("messageCode", "Car Year");
      } else {
         valid = true;
      }

      return valid;

   }

   @Override
   public CarResponse post(CarRequest req, String username) {

      CarEntity cekKey = repoCar.findById(req.getCarCd()).orElse(null);

      if (cekKey != null && cekKey.getDeletedFlag()) {
         throw new ValidationException("messageCode", "Car Code");
      }
      CarEntity carEntity = new CarEntity();
      CarComparisonEntity carCompEntity = new CarComparisonEntity();
      CarComparisonReq reqComparison = req.getCarComparison();
      Boolean isValid = this.validation(req);
      if (isValid) {
         BeanUtils.copyProperties(req, carEntity);
         carEntity.setCarCd(req.getCarCd());
         this.setCreateCommon(carEntity, username);
         repoCar.save(carEntity);
         BeanUtils.copyProperties(reqComparison, carCompEntity);
         carCompEntity.setCarCd(req.getCarCd());
         // carCompEntity.setCarFuelConsumption(reqComparison.getCarFuelConsumption());
         this.setCreateCommonD(carCompEntity, username);
         repoCarComparison.save(carCompEntity);
      }

      CarResponse response = new CarResponse();
      BeanUtils.copyProperties(carEntity, response);
      response.setCarComparison(carCompEntity);

      return response;
   }

   @Override
   public CarResponse get(String carCd) {

      if (carCd.isEmpty()) {
         throw new ValidationException("messageCode", "Car Code");
      }
      CarEntity cekKey = repoCar.findById(carCd).orElse(null);
      if (cekKey == null || cekKey.getDeletedFlag()) {
         throw new ValidationException("messageCode", "Car Code");
      }

      CarComparisonEntity findComparison = repoCarComparison.findById(carCd).orElse(null);

      CarResponse response = new CarResponse();
      BeanUtils.copyProperties(cekKey, response);
      response.setCarComparison(findComparison);

      return response;
   }

}
