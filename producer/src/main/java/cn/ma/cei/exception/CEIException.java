package cn.ma.cei.exception;

public class CEIException extends RuntimeException {
    public CEIException(String errMsg) {
        super(errMsg);
    }

    public enum ErrorType {
        PARSE_ERROR,
    }
}
