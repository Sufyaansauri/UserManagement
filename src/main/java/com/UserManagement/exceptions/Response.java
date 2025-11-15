package com.UserManagement.exceptions;


import java.util.Map;

public class Response {
    private String message;
    private String code;
    private Map<String, Object> data;

    public Response(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public Response(String message, String code, Map<String, Object> data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public Response() {
    }

    // New helper method for UMSResponse
    public void setResponse(UMSResponse umsResponse) {
        this.message = umsResponse.getMessage();
        this.code = umsResponse.getCode();
    }

    // Getters & Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(String key, Object data) {
        this.data = Map.of(key, data);
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
