package cn.ma.cei.exception;

public class CEIErrors {
    public static void showFailure(CEIErrorType errorType, String message, Object... args) throws CEIException {
        String res = String.format(message, args);
        throw new CEIException(res);
    }

    public static void showWarning(CEIErrorType errorType, String message, Object... args) {

    }

    public static void showInfo(CEIErrorType errorType, String message, Object... args) {

    }

    public static void showDebug(CEIErrorType errorType, String message, Object... args) {

    }
}
