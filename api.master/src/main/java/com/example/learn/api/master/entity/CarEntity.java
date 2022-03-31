package com.example.learn.api.master.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.example.learn.api.master.base.entity.CommonEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_CAR")
public class CarEntity extends CommonEntity {

   @Id
   @Column(name = "car_cd", nullable = false)
   private String carCd;

   @Column(name = "car_name")
   private String carName;

   @Column(name = "car_type")
   private String carType;

   @Column(name = "car_color")
   private String carColor;

   @Column(name = "car_transmisi")
   private String carTransmisi;

   @Column(name = "car_year")
   private Integer carYear;
}
