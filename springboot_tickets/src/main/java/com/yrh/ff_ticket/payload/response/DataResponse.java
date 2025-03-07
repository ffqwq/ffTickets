package com.yrh.ff_ticket.payload.response;

//后端往前端抛response
public class DataResponse<T> {
    private T data;
    private boolean success;
    private String errorMsg;//错误信息
    public DataResponse(){
        data=null;
        success=true;
        errorMsg=null;
    }
    public DataResponse(T data){
        this.data=data;
        this.success=true;
        this.errorMsg=null;
    }
    public DataResponse(boolean success,String errorMsg){
        this.data=null;
        this.success=success;
        this.errorMsg=errorMsg;
    }
    public DataResponse(T data,boolean success,String errorMsg){
        this.data=data;
        this.success=success;
        this.errorMsg=errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
