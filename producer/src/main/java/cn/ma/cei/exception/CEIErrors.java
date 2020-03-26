package cn.ma.cei.exception;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.model.base.xElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CEIErrors {
    private static Logger logger = LogManager.getLogger(CEIErrors.class);

    public static void showCodeFailure(Class<?> cls, String message, Object... args) throws CEIException {
        String res = String.format(message, args);
        throw new CEIException(String.format("[%s] %s", cls.getName(), res));
    }

    public static void showFailure(xElement element, String message, Object... args) throws CEIException {
        String res = String.format(message, args);
        throw new CEIException(res);
    }

    public static void showFailure(CEIErrorType errorType, String message, Object... args) throws CEIException {
        String res = String.format(message, args);
        if (errorType == CEIErrorType.CODE)
        logger.fatal(BuilderContext.getCurrentLanguage().getName() + ": " + res);
        throw new CEIException(res);
    }

    public static void showWarning(CEIErrorType errorType, String message, Object... args) {

    }

    public static void showInfo(String message, Object... args) {
        logger.info(String.format(message, args));
    }

    public static void showDebug(String message, Object... args) {
        logger.debug(String.format(message, args));
    }
}
