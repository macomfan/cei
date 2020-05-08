package cn.ma.cei.langs.java.tools;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.CEIPath;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.langs.java.JavaCode;
import cn.ma.cei.utils.UniqueList;

import java.util.*;

public class JavaClass {

    private final UniqueList<String, Variable> privateMemberList = new UniqueList<>();
    private final UniqueList<String, Variable> publicMemberList = new UniqueList<>();
    private final Set<String> importList = new HashSet<>();
    private final UniqueList<String, JavaMethod> methodList = new UniqueList<>();
    private final UniqueList<String, JavaClass> innerClasses = new UniqueList<>();
    JavaCode code = new JavaCode();
    private String className = "";
    private String packageName = "";
    private ClassType type = ClassType.STANDARD;

    public JavaClass(String className, String packageName) {
        this.className = className;
        this.packageName = packageName.toLowerCase();
    }

    public JavaClass(String className) {
        this.className = className;
        this.packageName = null;
        this.type = ClassType.INNER;
    }

    public String getClassName() {
        return className;
    }

    public void addInnerClass(JavaClass innerClass) {
        if (innerClass.type != ClassType.INNER) {
            innerClass.type = ClassType.INNER;
            innerClass.packageName = null;
        }
        if (innerClasses.containsKey(innerClass.className)) {
            CEIErrors.showCodeFailure(this.getClass(),"Cannot add duplicate class");
        }
        innerClasses.put(innerClass.className, innerClass);
    }

    public void addMemberVariable(Variable memberVariable) {
        if (memberVariable.position == Variable.Position.MEMBER) {
            publicMemberList.put(memberVariable.getName(), memberVariable);
        } else if (memberVariable.position == Variable.Position.PRIVATE) {
            privateMemberList.put(memberVariable.getName(), memberVariable);
        }
        addReference(memberVariable.getType());
    }

    public void addMethod(JavaMethod method) {
        methodList.put(method.getMethodName(), method);
    }

    public void build(CEIPath folder) {
        if (type == ClassType.INNER) {
            defineClass(className, () -> {
                writeMemberVariable(code);
                writeMethods(code);
            });
        } else {
            writeReference(code);

            defineClass(className, () -> {
                code.endln();
                innerClasses.values().forEach(value -> {
                    value.build(folder);
                    code.appendCode(value.code);
                    code.endln();
                });
                writeMemberVariable(code);
                writeMethods(code);
            });

            CEIPath file = CEIPath.appendFile(folder, className + ".java");
            file.write(code.toString());
        }
    }

    public void addReference(VariableType type) {
        importList.addAll(type.getReferences());
    }

    private void writeMethods(JavaCode code) {
        if (methodList.isEmpty()) {
            return;
        }
        methodList.values().forEach(method -> {
            code.endln();
            code.appendCode(method.getCode());
        });
    }

    private void writeReference(JavaCode code) {
        code.appendPackage(packageName);
        code.endln();

        Set<String> newImportList = new HashSet<>();

        innerClasses.values().forEach(value -> {
            newImportList.addAll(value.importList);
        });
        newImportList.addAll(importList);

        List<String> list = new ArrayList<>(newImportList);
        Collections.sort(list);
        list.forEach((item) -> {
            if (!item.equals(BuilderContext.NO_REF) && !item.equals(packageName)) {
                code.appendImport(item);
            }
        });
        code.endln();
    }

    private void writeMemberVariable(JavaCode code) {
        publicMemberList.values().forEach(variable -> {
            code.appendJavaLine("public", variable.getTypeDescriptor(), variable.getDescriptor());
        });
        privateMemberList.values().forEach(variable -> {
            code.appendJavaLine("private final", variable.getTypeDescriptor(), variable.getDescriptor());
        });
    }

    private void defineClass(String clsName, ClassContent classContent) {
        String header;
        if (type == ClassType.INNER) {
            header = "static public class";
        } else {
            header = "public class";
        }
        code.appendWordsln(header, clsName, "{");

        code.newBlock(() -> {
            classContent.inClass();
        });
        code.appendln("}");
    }

    enum ClassType {
        STANDARD,
        INNER
    }

    @FunctionalInterface
    private interface ClassContent {
        void inClass();
    }
}
