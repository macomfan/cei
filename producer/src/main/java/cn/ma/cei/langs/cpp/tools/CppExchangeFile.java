package cn.ma.cei.langs.cpp.tools;

import cn.ma.cei.generator.CEIPath;
import cn.ma.cei.langs.cpp.CppCode;
import cn.ma.cei.utils.UniqueList;

import java.util.HashSet;
import java.util.Set;

public class CppExchangeFile {
    private String exchangeName;
    private CodeForHpp codeH = new CodeForHpp();
    private CodeForCpp codeCpp = new CodeForCpp();
    private CEIPath cppPath;
    private CEIPath hPath;

    private UniqueList<String, CppClass> classList = new UniqueList<>();

    public CppExchangeFile(String exchangeName, CEIPath cppPath, CEIPath hPath) {
        this.exchangeName = exchangeName;
        this.cppPath = cppPath;
        this.hPath = hPath;
    }

    public void addClass(CppClass cppClass) {
        classList.put(cppClass.getClassName(), cppClass);
    }

    private void startNamespace() {
        codeH.appendln("#pragma once");
        codeH.endln();

        Set<String> includeSetH = new HashSet<>();
        Set<String> includeSetCpp = new HashSet<>();
        classList.values().forEach(item -> {
            includeSetH.addAll(item.getIncludeListH());
            includeSetCpp.addAll(item.getIncludeListCpp());
        });
        writeReference(codeH, includeSetH);
        includeSetCpp.add("\"cei/exchanges/" + exchangeName + ".hpp\"");
        writeReference(codeCpp, includeSetCpp);
        codeCpp.endln();

        codeH.appendWordsln("namespace", exchangeName, "{");
        codeCpp.appendWordsln("namespace", exchangeName, "{");

        codeCpp.startBlock();
        codeH.startBlock();
    }

    private void writeReference(CppCode code, Set<String> includeList) {
        includeList.forEach(code::appendInclude);
        codeH.endln();
    }

    private void endNamespace() {
        codeCpp.endBlock();
        codeCpp.appendln("}");
        codeH.endBlock();
        codeH.appendln("}");
    }

    public void build() {
        startNamespace();
        classList.values().forEach(item -> {
            item.build();
            codeH.appendCode(item.getCodeH());
            codeH.endln();

            if (!(item instanceof CppStruct)) {
                codeCpp.appendCode(item.getCode());
                codeCpp.endln();
            }
        });
        endNamespace();

        CEIPath cppFile = CEIPath.appendFile(cppPath, exchangeName + ".cpp");
        cppFile.write(codeCpp.toString());
        CEIPath hFile = CEIPath.appendFile(hPath, exchangeName + ".hpp");
        hFile.write(codeH.toString());
    }
}
