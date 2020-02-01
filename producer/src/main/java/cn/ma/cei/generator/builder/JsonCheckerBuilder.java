package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.environment.Variable;

public abstract class JsonCheckerBuilder {

    public enum UsedFor{
        RETURN_RESULT,
        REPORT_ERROR,
        UNKNOWN
    }

    private UsedFor usedFor = UsedFor.UNKNOWN;
    public void setUsedFor(UsedFor usedFor) {
        this.usedFor = usedFor;
    }

    /**
     * jsonChecker = new JsonChecker(jsonParser)
     *
     * @param jsonChecker
     * @param jsonParser
     */
    public abstract void defineJsonChecker(Variable jsonChecker, Variable jsonParser);

    public abstract void setNotEqual(Variable jsonChecker, Variable key, Variable value);

    public abstract void setEqual(Variable jsonChecker, Variable key, Variable value);

    public abstract void returnResult(Variable jsonChecker);

    public abstract void reportError(Variable jsonChecker);

    public void completeJsonChecker(Variable jsonChecker) {
        if (usedFor == UsedFor.REPORT_ERROR) {
            reportError(jsonChecker);
        } else if (usedFor == UsedFor.RETURN_RESULT) {
            returnResult(jsonChecker);
        }
    }
}
