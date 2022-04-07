package com.example.learn.api.master.request;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarRequest {

   @Size(max = 50)
   private String carCd;
   @Size(max = 50)
   private String carName;
   @Size(max = 50)
   private String carType;
   @Size(max = 50)
   private String carColor;
   @Size(max = 50)
   private String carTransmisi;
   private Integer carYear;

   private CarComparisonReq carComparison;

}
