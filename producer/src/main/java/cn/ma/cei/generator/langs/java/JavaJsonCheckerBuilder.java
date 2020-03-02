package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;

public class JavaJsonCheckerBuilder implements IJsonCheckerBuilder {

    private final JavaMethod method;

    public JavaJsonCheckerBuilder(JavaMethod method) {
        this.method = method;
    }

    @Override
    public void defineJsonChecker(Variable jsonChecker, Variable jsonParser) {
        method.addAssign(method.defineVariable(jsonChecker), method.newInstance(jsonChecker.getType(), jsonParser));
    }

    @Override
    public void setNotEqual(Variable jsonChecker, Variable key, Variable value) {
        method.addInvoke(jsonChecker.getDescriptor() + ".notEqual",  key, value);
    }

    @Override
    public void setEqual(Variable jsonChecker, Variable key, Variable value) {
        method.addInvoke(jsonChecker.getDescriptor() + ".equal",  key, value);
    }

    @Override
    public void returnResult(Variable jsonChecker) {
        method.addReturn(jsonChecker.getDescriptor() + ".complete()");
    }

    @Override
    public void reportError(Variable jsonChecker) {
        method.addInvoke(jsonChecker.getDescriptor() + ".reportError");
    }
}
