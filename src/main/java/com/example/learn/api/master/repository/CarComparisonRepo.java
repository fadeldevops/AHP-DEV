package com.example.learn.api.master.repository;

import com.example.learn.api.master.entity.CarComparisonEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarComparisonRepo extends JpaRepository<CarComparisonEntity, String> {

      @Query(value = " SELECT car_cd, car_year, car_price, car_age, car_fuel_consumption, car_mileage, car_capacity "
                  + " created_by, created_dt, changed_by, changed_dt "
                  + " FROM public.tb_car_comparison where car_cd = :carCd ", nativeQuery = true)
      Object[] getCar(@Param("carCd") String carCd);

      @Query(value = " select count(1) from public.tb_car_pairwise_comparison_matrix_sum WHERE car_type= :carType  ", nativeQuery = true)
      Integer cekCountCarType(@Param("carType") String carType);
}
