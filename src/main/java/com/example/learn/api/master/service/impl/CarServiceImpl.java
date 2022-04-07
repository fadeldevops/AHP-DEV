package com.example.learn.api.master.service.impl;

import java.util.Calendar;

import com.example.learn.api.master.base.response.SearchResponse;
import com.example.learn.api.master.base.service.impl.BaseServiceImpl;
import com.example.learn.api.master.entity.CarComparisonEntity;
import com.example.learn.api.master.entity.CarEntity;
import com.example.learn.api.master.exception.ValidationException;
import com.example.learn.api.master.repository.CarComparisonRepo;
import com.example.learn.api.master.repository.CarRepo;
import com.example.learn.api.master.request.CarComparisonReq;
import com.example.learn.api.master.request.CarRequest;
import com.example.learn.api.master.request.SearchCarReq;
import com.example.learn.api.master.response.CarResponse;
import com.example.learn.api.master.service.CarService;
import com.example.learn.api.master.service.CarComparisonMatriksService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
public class CarServiceImpl extends BaseServiceImpl implements CarService {

   @Autowired
   CarRepo repoCar;

   @Autowired
   CarComparisonRepo repoCarComparison;

   @Autowired
   CarComparisonMatriksService serviceCarSum;

   @Override
   public CarResponse post(CarRequest req, String username) {

      CarEntity cekKey = repoCar.findById(req.getCarCd()).orElse(null);

      if (cekKey != null && !cekKey.getDeletedFlag()) {
         throw new ValidationException("COMMNERR00009", "Car Code", req.getCarCd());
      }
      CarEntity carEntity = new CarEntity();
      CarComparisonEntity carCompEntity = new CarComparisonEntity();
      CarComparisonReq reqComparison = req.getCarComparison();
      // time now
      Calendar kalender = Calendar.getInstance();
      // get Year Calender
      int yearNow = kalender.get(Calendar.YEAR);

      Boolean isValid = this.validation(req);
      if (isValid) {

         BeanUtils.copyProperties(req, carEntity);
         carEntity.setCarCd(req.getCarCd());
         this.setCreateCommon(carEntity, username);
         repoCar.save(carEntity);

         BeanUtils.copyProperties(reqComparison, carCompEntity);
         carCompEntity.setCarCd(req.getCarCd());
         carCompEntity.setCarAge(yearNow - req.getCarYear());
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
         throw new ValidationException("COMMNERR00034", "Car Code");
      }
      CarEntity cekKey = repoCar.findById(carCd).orElse(null);
      if (cekKey == null || cekKey.getDeletedFlag()) {
         throw new ValidationException("COMMNERR00006", "Car Code", carCd);
      }

      CarComparisonEntity findComparison = repoCarComparison.findById(carCd).orElse(null);

      CarResponse response = new CarResponse();
      BeanUtils.copyProperties(cekKey, response);
      response.setCarComparison(findComparison);

      serviceCarSum.insertValue(cekKey.getCreatedBy());

      return response;
   }

   @Transactional
   @Override
   public CarResponse put(CarRequest req, String username) {

      CarEntity findId = repoCar.findById(req.getCarCd()).orElse(null);

      if (findId == null || findId.getDeletedFlag()) {
         throw new ValidationException("COMMNERR00006", "Car Code", req.getCarCd());
      }

      CarComparisonEntity findIdComparison = repoCarComparison.findById(req.getCarCd()).orElse(null);

      // CarComparisonEntity carCompEntity = new CarComparisonEntity();
      CarComparisonReq reqComparison = req.getCarComparison();
      Boolean isValid = this.validation(req);
      // time now
      Calendar kalender = Calendar.getInstance();
      // get Year Calender
      int yearNow = kalender.get(Calendar.YEAR);

      if (isValid) {
         BeanUtils.copyProperties(req, findId);
         this.setUpdateCommon(findId, username);
         repoCar.save(findId);

         BeanUtils.copyProperties(reqComparison, findIdComparison);
         findIdComparison.setCarAge(yearNow - req.getCarYear());
         this.setUpdateCommonD(findIdComparison, username);
         repoCarComparison.save(findIdComparison);
      }

      CarResponse response = new CarResponse();
      BeanUtils.copyProperties(findId, response);
      response.setCarComparison(findIdComparison);

      return response;
   }

   @SuppressWarnings("unchecked")
   @Override
   public SearchResponse<CarEntity> search(SearchCarReq req) {

      Page<CarEntity> page = repoCar.search(this.likeSearchConvertString(req.getCarCd()),
            this.likeSearchConvertString(req.getCarName()), this.getPageable(req));

      return this.createSearchResponse(page, req);
   }

   private Boolean validation(CarRequest req) {

      Boolean valid = false;

      if (StringUtils.isEmpty(req.getCarCd())) {
         throw new ValidationException("COMMNERR00034", "Car Code");
      } else {
         valid = true;
      }

      if (StringUtils.isEmpty(req.getCarName())) {
         throw new ValidationException("COMMNERR00034", "Car Name");
      } else {
         valid = true;
      }

      if (req.getCarYear() == null) {
         throw new ValidationException("COMMNERR00034", "Car Year");
      } else {
         valid = true;
      }

      if (req.getCarComparison() == null) {
         throw new ValidationException("COMMNERR00034", "Car Comparison");
      } else {
         valid = true;
      }

      if (req.getCarComparison().getCarCapacity() == null) {
         throw new ValidationException("COMMNERR00034", "Car Capacity");
      } else {
         valid = true;
      }
      if (req.getCarComparison().getCarFuelConsumption() == null) {
         throw new ValidationException("COMMNERR00034", "Car Fuel Consumption");
      } else {
         valid = true;
      }
      if (req.getCarComparison().getCarMileage() == null) {
         throw new ValidationException("COMMNERR00034", "Car Mileage");
      } else {
         valid = true;
      }

      return valid;

   }

}
