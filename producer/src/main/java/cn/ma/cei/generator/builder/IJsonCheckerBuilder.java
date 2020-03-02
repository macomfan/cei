package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;

public interface IJsonCheckerBuilder {

    enum UsedFor{
        RETURN_RESULT,
        REPORT_ERROR,
        UNDEFINED
    }

    /**
     * jsonChecker = new JsonChecker(jsonParser)
     *
     * @param jsonChecker
     * @param jsonParser
     */
    void defineJsonChecker(Variable jsonChecker, Variable jsonParser);

    void setNotEqual(Variable jsonChecker, Variable key, Variable value);

    void setEqual(Variable jsonChecker, Variable key, Variable value);

    void returnResult(Variable jsonChecker);

    void reportError(Variable jsonChecker);
}
