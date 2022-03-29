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
@Table(name = "TB_M_EMPLOYE")
public class EmployeeEntity extends CommonEntity {

   @Id
   @Column(name = "employe_cd", insertable = false, updatable = false, nullable = false)
   private String employeCd;

   @Column(name = "EMPLOYE_NAME")
   private String employeName;

   @Column(name = "employe_addr")
   private String employeAddr;

   @Column(name = "employe_contact_no")
   private String employeContacNo;

   @Column(name = "employe_desc")
   private String employeDesc;

}
