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
@Table(name = "TB_MENU")
public class MenuEntity extends CommonEntity {

   @Id
   @Column(name = "menu_cd", nullable = false)
   private String menuCd;

   @Column(name = "menu_name")
   private String menuName;

   @Column(name = "menu_type_cd")
   private String menuTypeCd;

   @Column(name = "parent_menu_cd")
   private String parentMenuCd;

}
