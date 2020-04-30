package cn.ma.cei.langs.python3.processor;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.langs.python3.tools.Python3Method;

public class Python3JsonCheckerBuilder implements IJsonCheckerBuilder {

    private final Python3Method method;

    public Python3JsonCheckerBuilder(Python3Method method) {
        this.method = method;
    }


    @Override
    public void defineJsonChecker(Variable jsonChecker) {
        method.addAssign(method.defineVariable(jsonChecker), method.newInstance(jsonChecker.getType()));
    }

    @Override
    public void setNotEqual(Variable jsonChecker, Variable key, Variable value, Variable jsonWrapperObject) {
        method.addInvoke(jsonChecker.getDescriptor() + ".not_equal",  key, value);
    }

    @Override
    public void setEqual(Variable jsonChecker, Variable key, Variable value, Variable jsonWrapperObject) {
        method.addInvoke(jsonChecker.getDescriptor() + ".equal",  key, value);
    }

    @Override
    public void setContainKey(Variable jsonChecker, Variable key, Variable jsonWrapperObject) {

    }

    @Override
    public void returnResult(Variable jsonChecker) {
        method.addReturn(jsonChecker.getDescriptor() + ".complete()");
    }

    @Override
    public void reportError(Variable jsonChecker) {
        method.addInvoke(jsonChecker.getDescriptor() + ".report_error");
    }
}
