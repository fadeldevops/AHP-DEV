package com.example.learn.api.master.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
// @IdClass(UserProfileId.class)
@Entity
@Table(name = "TB_M_USER_PROFILE")
public class UserProfileEntity extends CommonEntity {

   @Id
   @Column(name = "username", nullable = false)
   private String username;

   @Column(name = "person_name", nullable = false)
   private String personName;

   @Column(name = "email", nullable = false)
   private String email;

   @Column(name = "phone_no", nullable = false)
   private String phoneNo;

}
