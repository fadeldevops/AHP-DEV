package com.example.learn.api.master.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarResponse {

   private String carCd;
   private String carName;
   private String carType;
   private String carColor;
   private String carTransmisi;
   private String createdBy;
   private Date createdDt;
   private String changedBy;
   private Date changedDt;
   private Boolean deletedFlag;

   private CarComparisonResponse carComparison;

   private CarImagesResponse carImage;

}
