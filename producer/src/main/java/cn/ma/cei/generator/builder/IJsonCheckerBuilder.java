package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;

public interface IJsonCheckerBuilder extends IBuilderBase {

    enum UsedFor{
        RETURN_RESULT,
        REPORT_ERROR,
        UNDEFINED
    }

    /**
     * jsonChecker = new JsonChecker(jsonParser)
     *
     * @param jsonChecker
     */
    void defineJsonChecker(Variable jsonChecker);

    void setNotEqual(Variable jsonChecker, Variable key, Variable value, Variable jsonWrapperObject);

    void setEqual(Variable jsonChecker, Variable key, Variable value, Variable jsonWrapperObject);

    void setContainKey(Variable jsonChecker, Variable key, Variable jsonWrapperObject);

    void returnResult(Variable jsonChecker);

    void reportError(Variable jsonChecker);
}
