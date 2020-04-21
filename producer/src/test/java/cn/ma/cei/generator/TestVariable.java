package cn.ma.cei.generator;

import cn.ma.cei.langs.test.testFramework;
import cn.ma.cei.model.types.xString;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TestVariable {
    @Test
    public void test() {
        testFramework framework = new testFramework();
        GlobalContext.setCurrentExchange(framework.getLanguage().getName());
        GlobalContext.setCurrentLanguage(framework.getLanguage());
        GlobalContext.setCurrentFramework(framework);
        GlobalContext.setupBuildInVariableType(xString.typeName, "string", "string.test");
        GlobalContext.setupRunTimeVariableType("A", "test.A");
        GlobalContext.setupRunTimeVariableType("B", "test.B");
        GlobalContext.setupRunTimeVariableType("C", "test.C");
        VariableType modelA = GlobalContext.variableType("A");
        VariableType modelB = GlobalContext.variableType("B");
        VariableType modelC = GlobalContext.variableType("C");
        modelA.addMember(xString.inst.getType(), "str_a");
        modelA.addMember(modelB, "b");
        modelB.addMember(xString.inst.getType(), "str_b");
        modelB.addMember(modelC, "c");
        modelC.addMember(xString.inst.getType(), "str_c");

        Variable a = VariableCreator.createLocalVariable(modelA, "a");
        assertEquals("a", a.getDescriptor());
        Variable ab = a.getMember("b");
        assertEquals("a.b", ab.getDescriptor());
        Variable abc = ab.getMember("c");
        assertEquals("a.b.c", abc.getDescriptor());
        Variable abc_str_c = abc.getMember("str_c");
        assertEquals("a.b.c.str_c", abc_str_c .getDescriptor());
    }
}
