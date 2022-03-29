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
@IdClass(RoleMenuId.class)
@Table(name = "TB_ROLE_MENU")
public class RoleMenuEntity extends CommonEntity {

   @Id
   @Column(name = "menu_cd", nullable = false)
   private String menuCd;

   @Id
   @Column(name = "role_cd", nullable = false)
   private String roleCd;

}
