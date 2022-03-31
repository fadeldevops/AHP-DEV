package com.example.learn.api.master.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarComparisonReq {

   private Integer carPrice;
   private Integer carAge;
   private Double carFuelConsumption;
   private BigDecimal carMileage;
   private Integer carCapacity;

}
