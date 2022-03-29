package com.example.learn.api.master.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@IdClass(UserEmpId.class)
@Table(name = "TB_USER_EMP")
public class UserEmployeEntity extends CommonEntity {

   @Id
   @Column(name = "username", nullable = false)
   private String username;

   @Id
   @Column(name = "employe_cd")
   private String employeCd;

   @Column(name = "person_name")
   private String personName;

   @Column(name = "email")
   private String email;

   @Column(name = "phone_no")
   private String phoneNo;

   @Column(name = "position")
   private String position;

}