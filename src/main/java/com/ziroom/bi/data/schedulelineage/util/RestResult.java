package com.ziroom.bi.data.schedulelineage.util;

import com.ziroom.bi.data.schedulelineage.constant.ResponseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@ApiModel("api通用返回数据")
@Slf4j
public class RestResult<T> implements Serializable {

    //标识代码，0表示成功，非0表示出错
    @ApiModelProperty("标识代码,0表示成功，非0表示出错")
    private Integer code;

    //提示信息，通常供报错时使用
    @ApiModelProperty("提示信息,供报错时使用")
    private String msg;

    //正常返回时返回的数据
    @ApiModelProperty("返回的数据")
    private T data;

    //constructor
    public RestResult() {
    }

    //constructor
    public RestResult(Integer status, String msg, T data) {
        this.code = status;
        this.msg = msg;
        this.data = data;
    }

    public static RestResult success(){
        return new RestResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), null);
    }

    //返回成功数据
    public static <T> RestResult<T>  success(T data) {
        return new RestResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), data);
    }

    public static RestResult success(Integer code,String msg) {
        return new RestResult(code, msg, null);
    }

    //返回出错数据
    public static RestResult error(ResponseCode code) {
        return new RestResult(code.getCode(), code.getMsg(), null);
    }
    //返回出错数据
    public static <T> RestResult<T> error(Exception e) {
        log.error("error find",e);
        return new RestResult(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMsg(), e);
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

}
