package com.example.learn.api.master.service.impl;

import com.example.learn.api.master.base.service.impl.BaseServiceImpl;
import com.example.learn.api.master.repository.CarComparisonRepo;
import com.example.learn.api.master.repository.CarImagesRepo;
import com.example.learn.api.master.repository.CarRepo;
import com.example.learn.api.master.service.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class CarServiceImpl extends BaseServiceImpl implements CarService {

   @Autowired
   CarRepo repoCar;

   @Autowired
   CarComparisonRepo repoCarComparison;

   @Autowired
   CarImagesRepo repoCarImages;

}
