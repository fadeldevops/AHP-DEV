package com.example.learn.api.master.response;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarComparisonResponse {

   private Integer carYear;
   private BigInteger carPrice;
   private Integer carAge;
   private Double carFuelConsumption;
   private BigDecimal carMileage;
   private Integer carCapacity;
   private String createdBy;
   private Date createdDt;
   private String changedBy;
   private Date changedDt;

}
