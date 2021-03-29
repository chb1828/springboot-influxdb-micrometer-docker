package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class JSONResult {

    private String result; //Success ,Fail
    private String message; // 실패시 message
    private Object data; // 성공시 돌려줄 데이터

    public static JSONResult success(Object data) {
        return new JSONResult("success",null,data);
    }

    public static JSONResult success(Object data,String message) {
        return new JSONResult("success",message,data);
    }

    public static JSONResult fail(String message) {
        return new JSONResult("fail",message,null);
    }
}