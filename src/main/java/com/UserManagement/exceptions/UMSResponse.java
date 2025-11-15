package com.UserManagement.exceptions;


public enum UMSResponse{
    USER_NOT_FOUND("User not found", "404"),
    INVALID_USER_DATA("Invalid user data","400"),
    USER_ALREADY_EXISTS("User already exists","409"),
    OPERATION_SUCCESS("successful","200"),
    USER_UPDATED_SUCCESSFULLY("User Updated Successfully","201" ),
    USER_LOGIN_SUCCESS("User logged in successfully","200"),
    USER_LOGIN_FAILED("User login failed","401"),
    USER_CREATED_SUCCESSFULLY("User created successfully","201"),
    USER_DATA_NULL("User data is null","400"),
    INVALID_EMAIL_FORMAT("Invalid email format","400"),
    EMAIL_NULL("Email is null or empty","400"),
    OTP_SENT_SUCCESSFULLY("OTP sent successfully","200"),
    OTP_VALID("OTP is valid","200"),
    OTP_INVALID("OTP is invalid","400"),
    OTP_EXPIRED("OTP is expired","400"),
    PASSWORD_MISMATCH("Password not match!","400"),
    PRODUCT_ALREADY_EXISTS("Product already exists","409"),
    PRODUCT_CREATED_SUCCESSFULLY("Product created successfully","201"),
    PRODUCT_NOT_FOUND("Product not found","404"),
    INTERNAL_SERVER_ERROR("Internal server error","500");


    private final String message;
    private final String code;


    UMSResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }
    public String getCode() {
        return code;
    }
}
