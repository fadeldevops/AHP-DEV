package com.example.learn.api.master.vo;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
   @NonNull
   @NotBlank
   private String username;
   @NonNull
   @NotBlank
   private String password;

}
