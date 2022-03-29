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
@Table(name = "tb_car_image")
public class CarImagesEntity extends CommonEntity {

   @Id
   @Column(name = "car_cd", nullable = false)
   private String carCd;

   @Column(name = "car_image")
   private String carImage;

}
