package com.example.learn.api.master.repository;

import com.example.learn.api.master.entity.CarImagesEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarImagesRepo extends JpaRepository<CarImagesEntity, String> {

   @Query(value = " SELECT car_image,  "
         + " deleted_flag, created_by, created_dt, changed_by, changed_dt "
         + " FROM public.tb_car_image where car_cd = :carCd and deleted_flag = false ", nativeQuery = true)
   Object[] getCarImages(@Param("carCd") String carCd);

}
