package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.environment.Variable;

public abstract class JsonCheckerBuilder {
    /**
     * jsonChecker = new JsonChecker(jsonParser)
     *
     * @param jsonChecker
     * @param jsonParser
     */
    public abstract void defineJsonChecker(Variable jsonChecker, Variable jsonParser);

    public abstract void setNotEqual(Variable key, Variable value);

    public abstract void setEqual(Variable key, Variable value);

    public abstract void completeJsonChecker(Variable jsonParser);
}
