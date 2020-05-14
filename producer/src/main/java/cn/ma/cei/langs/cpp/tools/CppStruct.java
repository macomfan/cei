package cn.ma.cei.langs.cpp.tools;

import cn.ma.cei.exception.CEIErrors;


public class CppStruct extends CppClass {
    public CppStruct(String className) {
        super(className);
    }

    @Override
    public void addMethod(CppMethod method) {
        CEIErrors.showCodeFailure(CppStruct.class, "Cannot add method to CppStruct");
    }

    @Override
    public void build() {
        getCodeH().appendWordsln("struct", getClassName(), "{");
        if (!publicMemberList.isEmpty()) {
            getCodeH().newBlock(() -> {
                publicMemberList.values().forEach((variable) -> {
                    getCodeH().appendCppWordsln(variable.getTypeDescriptor(), variable.getDescriptor());
                });
            });
        }
        getCodeH().appendln("};");
    }
}
