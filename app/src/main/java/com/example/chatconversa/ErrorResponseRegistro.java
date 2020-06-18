package com.example.chatconversa;

import java.util.Objects;

public class ErrorResponseRegistro {

    private int status_code;
    private String message;
    private UserRespuestaErrorReg errors;

    public ErrorResponseRegistro(int status_code, String message, UserRespuestaErrorReg errors) {
        this.status_code = status_code;
        this.message = message;
        this.errors = errors;
    }

    public ErrorResponseRegistro() {

    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserRespuestaErrorReg getErrors() {
        return errors;
    }

    public void setErrors(UserRespuestaErrorReg errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponseRegistro that = (ErrorResponseRegistro) o;
        return status_code == that.status_code &&
                Objects.equals(message, that.message) &&
                Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status_code, message, errors);
    }

    @Override
    public String toString() {
        return "ErrorResponseRegistro{" +
                "status_code=" + status_code +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                '}';
    }
}
