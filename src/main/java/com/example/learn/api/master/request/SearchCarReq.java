package com.example.learn.api.master.request;

import com.example.learn.api.master.base.search.BaseSearchRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCarReq extends BaseSearchRequest {

   private String carCd;
   private String carName;

}
