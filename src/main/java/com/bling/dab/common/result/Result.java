package com.bling.dab.common.result;

import com.bling.dab.common.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: hxp
 * @date: 2019/2/15 19:46
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private boolean success;

    private Integer code;

    private String message;

    private  Object data;

    /**
     * 成功 不返回数据直接返回成功信息
     * @return
     */
    public static Result success() {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS.code());
        result.setMessage(ResultCode.SUCCESS.message());
        return result;
    }

    /**
     * 成功 并且加上返回数据
     * @param data
     * @return
     */
    public static Result success(Object data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS.code());
        result.setMessage(ResultCode.SUCCESS.message());
        result.setData(data);
        return result;
    }

    /**
     * 成功 自定义成功返回状态 加上数据
     * @param resultCode
     * @return
     */
    public static Result success(ResultCode resultCode) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(resultCode.code());
        result.setMessage(resultCode.message());
        return result;
    }

    /**
     * 成功 自定义成功返回状态 加上数据
     * @param resultCode
     * @param data
     * @return
     */
    public static Result success(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(resultCode.code());
        result.setMessage(resultCode.message());
        result.setData(data);
        return result;
    }

    /**
     * 成功 自定义成功返回状态 加上数据
     * @return
     */
    public static Result failure() {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.FAILURE.code());
        result.setMessage(ResultCode.FAILURE.message());
        return result;
    }

    /**
     * 返回失败的状态码 及 数据
     * @param data
     * @return
     */
    public static Result failure(Object data) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.FAILURE.code());
        result.setMessage(ResultCode.FAILURE.message());
        result.setData(data);
        return result;
    }

    /**
     * 单返回自定义失败的状态码
     * @param resultCode
     * @return
     */
    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(resultCode.code());
        result.setMessage(resultCode.message());
        return result;
    }

    /**
     * 返回自定义失败的状态码 及 数据
     * @param resultCode
     * @param data
     * @return
     */
    public static Result failure(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(resultCode.code());
        result.setMessage(resultCode.message());
        result.setData(data);
        return result;
    }
}
