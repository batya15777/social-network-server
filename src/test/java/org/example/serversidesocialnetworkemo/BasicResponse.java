package org.example.serversidesocialnetworkemo;

public class BasicResponse {
    public BasicResponse(boolean success, Integer errorCode) {
        this.success = success;
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    private boolean success;
    private Integer errorCode;





}
