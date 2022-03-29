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
@Table(name = "TB_ROLE")
public class RoleEntity extends CommonEntity {

   @Id
   @Column(name = "role_cd", nullable = false)
   private String roleCd;

   @Column(name = "role_name")
   private String roleName;

   @Column(name = "admin_flag")
   private Boolean adminFlag;

}
