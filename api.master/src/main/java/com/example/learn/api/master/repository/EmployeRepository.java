package com.example.learn.api.master.repository;

import com.example.learn.api.master.entity.EmployeeEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeRepository extends JpaRepository<EmployeeEntity, String> {

   @Query(value = " from EmployeeEntity m where m.deletedFlag = false "
         + "and (:employeCd = '' or lower(m.employeCd) like lower(concat(:employeCd, '%'))) "
         + "and (:employeName = '' or lower(m.employeName) like lower(concat(:employeName, '%'))) order by m.createdDt desc")
   Page<EmployeeEntity> search(@Param("employeCd") String employeCd, @Param("employeName") String employeName,
         Pageable pageable);

}
