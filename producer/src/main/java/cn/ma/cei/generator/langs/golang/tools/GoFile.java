/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang.tools;

import cn.ma.cei.generator.CEIPath;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.langs.golang.GoCode;
import cn.ma.cei.utils.UniquetList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author U0151316
 */
public class GoFile {

    private String filename;
    private String packageName;
    private GoCode code = new GoCode();

    private UniquetList<String, GoStruct> structList = new UniquetList<>();
    private UniquetList<String, GoMethod> methodList = new UniquetList<>();

    public GoFile(String filename, String packageName) {
        this.filename = filename;
        this.packageName = packageName;
    }

    public void addStruct(GoStruct struct) {
        structList.put(struct.getStructName(), struct);
    }

    public void addMethod(GoMethod method) {
        methodList.put(method.getMethodName(), method);
    }

    public void build(CEIPath folder) {
        code.addPackage(packageName);
        code.endln();
        writeReference();

        structList.values().forEach(struct -> {
            struct.build();
            code.appendCode(struct.getCode());
            code.endln();
        });

        methodList.values().forEach(method -> {
            code.appendCode(method.getCode());
            code.endln();
        });
        
        CEIPath file = CEIPath.appendFile(folder, filename + ".go");
        file.write(code.toString());
    }

    private void writeReference() {
        Set<String> newImportList = new HashSet<>();

        structList.values().forEach(value -> {
            newImportList.addAll(value.getImportList());
        });

        List<String> list = new ArrayList<>(newImportList);
        Collections.sort(list);
        code.appendln("import (");
        code.newBlock(() -> {
            list.forEach((item) -> {
                if (!item.equals(BuilderContext.NO_REF) && !item.equals(packageName)) {
                    code.appendln("\"" + item + "\"");
                }
            });
        });
        code.appendln(")");
        code.endln();
    }
}
