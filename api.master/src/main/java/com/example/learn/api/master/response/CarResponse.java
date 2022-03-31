package com.example.learn.api.master.response;

import com.example.learn.api.master.entity.CarComparisonEntity;
import com.example.learn.api.master.entity.CarEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarResponse extends CarEntity {

   // private String carCd;
   // private String carName;
   // private String carType;
   // private String carColor;
   // private String carTransmisi;
   // private String createdBy;
   // private Date createdDt;
   // private String changedBy;
   // private Date changedDt;
   // private Boolean deletedFlag;

   private CarComparisonEntity carComparison;

}
