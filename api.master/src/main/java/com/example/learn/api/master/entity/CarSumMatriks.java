package com.example.learn.api.master.entity;

import java.util.TreeMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarSumMatriks {

   private String carType;
   private TreeMap<String, Double> mapMatriks = new TreeMap<>();
   private TreeMap<String, Double> sumMatriks = new TreeMap<>();

}
