package cn.ma.cei.exception;

public class CEIException extends RuntimeException {
    private Class cls = null;
    private CEIErrorType errorType = CEIErrorType.UNKNOWN;

    public CEIException(String errMsg) {
        super(errMsg);
    }
    
    public CEIException(CEIErrorType type, Class cls, String errMsg) {
        super(String.format("[%s]-[%s]: %s", type.getName(), cls.getSimpleName(), errMsg));
        this.cls = cls;
        this.errorType = type;
    }
}
