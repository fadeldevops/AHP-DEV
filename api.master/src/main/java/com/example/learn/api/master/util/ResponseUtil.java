package com.example.learn.api.master.util;

import java.util.Date;

import com.example.learn.api.master.base.response.Response;
import com.example.learn.api.master.base.response.ResponseStatus;

import org.springframework.stereotype.Component;

@Component
public class ResponseUtil {

   public <T> Response<T> generateResponseSuccess(String messageCode, T data, String... varValues) {
      Response<T> result = new Response<>(ResponseStatus.SUCCESS, new Date());
      result.setMessageCode(messageCode);
      result.setMessage("Processed Successfully");
      result.setData(data);

      return result;
   }

   public <T> Response<T> generateResponseSuccess(T data) {

      return this.generateResponseSuccess("COMMNINF00002", data);
   }

   public Response<Object> generateResponseSuccess() {
      return this.generateResponseSuccess(null);
   }

}
