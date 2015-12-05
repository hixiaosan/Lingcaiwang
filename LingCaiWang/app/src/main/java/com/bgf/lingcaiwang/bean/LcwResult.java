package com.bgf.lingcaiwang.bean;

import java.io.Serializable;

/**
 * 领财网请求结果
 *
 * @author Darren
 */
public class LcwResult<T> implements Serializable {

    private String msg;

    private int status;

    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int isStatus() {
        return status;
    }

    public boolean isSuccess() {
        return status == 1;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
