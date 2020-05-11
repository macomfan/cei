package cn.ma.cei.langs.java.processor;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.langs.java.tools.JavaMethod;

public class JavaJsonCheckerBuilder implements IJsonCheckerBuilder {

    private final JavaMethod method;

    public JavaJsonCheckerBuilder(JavaMethod method) {
        this.method = method;
    }

    @Override
    public void defineJsonChecker(Variable jsonChecker) {
        method.addAssign(method.defineVariable(jsonChecker), method.newInstance(jsonChecker.getType()));
    }

    @Override
    public void setNotEqual(Variable jsonChecker, Variable key, Variable value, Variable jsonWrapperObject) {
        method.addInvoke(jsonChecker.getDescriptor() + ".checkNotEqual",  key, value, jsonWrapperObject);
    }

    @Override
    public void setEqual(Variable jsonChecker, Variable key, Variable value, Variable jsonWrapperObject) {
        method.addInvoke(jsonChecker.getDescriptor() + ".checkEqual",  key, value, jsonWrapperObject);
    }

    @Override
    public void setContainKey(Variable jsonChecker, Variable key, Variable jsonWrapperObject) {
        method.addInvoke(jsonChecker.getDescriptor() + ".containKey",  key, jsonWrapperObject);
    }

    @Override
    public void setValueInclude(Variable jsonChecker, Variable key, Variable value, Variable jsonWrapperObject) {
        method.addInvoke(jsonChecker.getDescriptor() + ".valueInclude",  key, value, jsonWrapperObject);
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
