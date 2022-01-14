package com.langsin.oa.foundation;

import java.io.Serializable;

/**
 * 通用rest结果集返回
 *
 * @author wyy
 */
public class Result<T> implements Serializable {
    private int restCode;
    private String restInfo;
    private T data;

    public static <W> Result<W> success() {
        return new Result<>(RestCodeEnum.SUCCESS.getOrdinal(), RestCodeEnum.SUCCESS.getLabel());
    }

    public static <W> Result<W> success(W data) {
        return new Result<>(RestCodeEnum.SUCCESS.getOrdinal(), data);
    }

    public static <W> Result<W> success(String restInfo, W data) {
        return new Result<>(RestCodeEnum.SUCCESS.getOrdinal(), restInfo, data);
    }

    public static <W> Result<W> success(int restCode, String restInfo, W data) {
        return new Result<>(restCode, restInfo, data);
    }

    public static <W> Result<W> failed() {
        return new Result<>(RestCodeEnum.ERROR.getOrdinal(),"操作失败");
    }

    public static <W> Result<W> failed(String restInfo) {
        return new Result<>(RestCodeEnum.ERROR.getOrdinal(), restInfo);
    }

    public static <W> Result<W> failed(int code, String restInfo) {
        return new Result<>(code, restInfo);
    }

    public static <W> Result<W> failed(int code, String restInfo, W data) {
        return new Result<>(code, restInfo, data);
    }
    public Result() {
    }
    public Result(int restCode) {
        this.restCode = restCode;
    }

    public Result(int restCode, String restInfo) {
        this.restCode = restCode;
        this.restInfo = restInfo;
    }

    public Result(int restCode, T result) {
        this.restCode = restCode;
        this.data = result;
    }

    public Result(int restCode, String restInfo, T result) {
        this.restCode = restCode;
        this.restInfo = restInfo;
        this.data = result;
    }

    /**
     * 序列化的时候 忽略生成
     *
     * @return boolean
     */
    public boolean isSuccess() {
        return RestCodeEnum.SUCCESS.getOrdinal().equals(this.restCode);
    }

    public int getRestCode() {
        return restCode;
    }

    public void setRestCode(int restCode) {
        this.restCode = restCode;
    }

    public String getRestInfo() {
        return restInfo;
    }

    public void setRestInfo(String restInfo) {
        this.restInfo = restInfo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}