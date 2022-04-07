package com.example.learn.api.master.request;

import com.example.learn.api.master.base.search.BaseSearchRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeSearchRequest extends BaseSearchRequest {

   private String employeCd;
   private String employeName;

}
