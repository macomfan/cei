package cn.ma.cei.langs.golang.processor;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.langs.golang.tools.GoMethod;

public class GoJsonCheckerBuilder implements IJsonCheckerBuilder {
    private final GoMethod method;

    GoJsonCheckerBuilder(GoMethod method) {
        this.method = method;
    }

    @Override
    public void defineJsonChecker(Variable jsonChecker) {
        method.addAssignAndDeclare(method.useVariable(method.var(jsonChecker)), method.newInstance(jsonChecker.getType()));
    }

    @Override
    public void setNotEqual(Variable jsonChecker, Variable key, Variable value, Variable jsonWrapperObject) {
        method.addInvoke(jsonChecker.getDescriptor() + ".CheckNotEqual",
                method.var(key),
                method.var(value),
                method.var(jsonWrapperObject));
    }

    @Override
    public void setEqual(Variable jsonChecker, Variable key, Variable value, Variable jsonWrapperObject) {
        method.addInvoke(jsonChecker.getDescriptor() + ".CheckEqual",
                method.var(key),
                method.var(value),
                method.var(jsonWrapperObject));
    }

    @Override
    public void setContainKey(Variable jsonChecker, Variable key, Variable jsonWrapperObject) {
        method.addInvoke(jsonChecker.getDescriptor() + ".containKey", method.var(key), method.var(jsonWrapperObject));
    }

    @Override
    public void returnResult(Variable jsonChecker) {
        method.addReturn(method.var(BuilderContext.createStatement(jsonChecker.getDescriptor() + ".Complete()")));
    }

    @Override
    public void reportError(Variable jsonChecker) {

    }
}
