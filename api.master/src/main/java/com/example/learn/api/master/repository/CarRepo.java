package com.example.learn.api.master.repository;

import com.example.learn.api.master.entity.CarEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarRepo extends JpaRepository<CarEntity, String> {

   @Query(value = " SELECT car_cd, car_name, car_type, car_color, car_transmisi, "
         + " deleted_flag, created_by, created_dt, changed_by, changed_dt "
         + " FROM public.tb_car where car_cd = :carCd and deleted_flag = false ", nativeQuery = true)
   Object[] getCar(@Param("carCd") String carCd);

}
