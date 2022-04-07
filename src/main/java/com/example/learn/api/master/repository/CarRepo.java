package com.example.learn.api.master.repository;

import java.util.List;

import com.example.learn.api.master.entity.CarEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarRepo extends JpaRepository<CarEntity, String> {

      @Query(value = " SELECT car_cd, car_name, car_type, car_color, car_transmisi, "
                  + " deleted_flag, created_by, created_dt, changed_by, changed_dt "
                  + " FROM public.tb_car where car_cd = :carCd and deleted_flag = false ", nativeQuery = true)
      Object[] getCar(@Param("carCd") String carCd);

      @Query(value = "from CarEntity c where c.deletedFlag = false " +
                  "and (:carCd = '' or lower(c.carCd) like lower(concat('%', :carCd, '%'))) " +
                  "and (:carName = '' or lower(c.carName) like lower(concat('%', :carName, '%')))" +
                  " order by c.createdDt desc ")
      Page<CarEntity> search(@Param("carCd") String carCd, @Param("carName") String carName, Pageable pageable);

      @Query(value = "select " +
                  "	c.car_cd, " +
                  "	tcc.car_price, " +
                  "	tcc.car_age, " +
                  "	tcc.car_fuel_consumption, " +
                  "	tcc.car_mileage, " +
                  "	tcc.car_capacity " +
                  "from " +
                  "	public.tb_car c " +
                  "	inner join tb_car_comparison tcc on " +
                  "	c.car_cd = tcc.car_cd ", nativeQuery = true)
      List<Object[]> listAllCar();

}
