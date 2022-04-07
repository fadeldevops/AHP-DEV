package com.example.learn.api.master.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.example.learn.api.master.base.entity.CommonEntityD;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_COMMON")
public class CommonTblEntity extends CommonEntityD {

   @Id
   @Column(name = "type_cd", nullable = false)
   private String typeCd;

   @Column(name = "type_name")
   private String typeName;

   @Column(name = "type_value")
   private String typeValue;

}
