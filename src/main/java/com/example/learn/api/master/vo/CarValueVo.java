package com.example.learn.api.master.vo;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarValueVo {
   private String carCd;
   private BigInteger carPrice;
   private Short carAge;
   private Double carFuelConsumption;
   private BigDecimal carMileage;
   private Short carCapacity;

}
