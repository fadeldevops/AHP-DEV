package com.example.learn.api.master.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "TB_M_USER")
public class UserEntity extends CommonEntityD {

   @Id
   @Column(name = "username", nullable = false)
   private String username;

   @Column(name = "pass", nullable = false)
   private String pass;

}
