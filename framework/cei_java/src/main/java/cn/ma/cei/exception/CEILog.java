package cn.ma.cei.exception;

public class CEILog {
    public static void setLogLevel() {

    }

    public static void showFailure(String message, Object... args)  {
        throw new CEIException(String.format(message, args));
    }

    public static void showWarning(String message, Object... args) {

    }

    public static void showInfo(String message, Object... args) {

    }

    public static void showDebug(String message, Object... args) {

    }
}
