package com.why.transportsecurity.exception;

import lombok.Data;

/**
 * @ClassName：AdminException
 * @Description：todo 自定义用户异常
 * @Author: why
 * @DateTime：2021/12/11 16:17
 */
@Data
public class AdminException extends Exception {
    private Integer errorCode; //异常对应的返回码
    private String message; //异常信息

    public AdminException() {
        super();
    }

    public AdminException(String message) {
        super(message);
        this.message = message;
    }

    public AdminException( Integer errorCode,String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }
}
