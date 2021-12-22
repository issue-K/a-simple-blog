package com.cl.smyblog.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    private int resultCode;

    private String message;

    private Object data;

    public static Result success(Object data){
        Result result = new Result();
        result.setData( data );
        result.setMessage("success");
        result.setResultCode(200);
        return result;
    }

    public static Result fail(int resultCode,String message){
        Result result = new Result();
        result.setData( null );
        result.setMessage(message);
        result.setResultCode(resultCode);
        return result;
    }
}
