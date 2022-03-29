package com.example.learn.api.master.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {

   /**
   *
   */
   private static final long serialVersionUID = 1L;

   private String messageCode;
   private String[] varValues;

   public BusinessException(String messageCode, Throwable cause, String... varValues) {
      super(messageCode, cause);
      this.messageCode = messageCode;
      this.varValues = varValues;
   }

   public BusinessException(String messageCode, String... varValues) {
      super(messageCode);
      this.messageCode = messageCode;
      this.varValues = varValues;
   }

   public BusinessException(String messageCode, Throwable cause) {
      super(cause);
      this.messageCode = messageCode;
   }

}
