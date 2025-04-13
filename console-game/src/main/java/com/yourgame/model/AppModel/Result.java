package com.yourgame.model.AppModel;

public record Result(String message, boolean isSuccessful) {
    
     public static Result success(String message) {
         return new Result(message, true);
    }
    
    public static Result failure(String message) {
         return new Result(message, false);
    }
    
    @Override
    public String toString() {
         return message;
    }
   
    public String getMessage() {
      return message;
      }
 
 }