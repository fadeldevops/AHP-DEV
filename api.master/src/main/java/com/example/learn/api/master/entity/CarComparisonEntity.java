package com.example.learn.api.master.entity;

import com.example.learn.api.master.base.entity.CommonEntityD;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_car_comparison")
public class CarComparisonEntity extends CommonEntityD {

   @Id
   @Column(name = "car_cd")
   private String carCd;

   @Column(name = "car_price")
   private Integer carPrice;

   @Column(name = "car_age")
   private Integer carAge;

   @Column(name = "car_fuel_consumption")
   private Double carFuelConsumption;

   @Column(name = "car_mileage")
   private BigDecimal carMileage;

   @Column(name = "car_capacity")
   private Integer carCapacity;
}
