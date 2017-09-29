package io.kevintong.ireader.network.entity;

public class HttpResult<T> {


    /**
     * msg : SUCCESS
     * status_code : 0
     * data : {}
     */

    private String msg;
    private int status_code;
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static class DataBean {
    }
}
