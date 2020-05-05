package cn.ma.cei.langs.python3.tools;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.CEIPath;
import cn.ma.cei.langs.python3.Python3Code;
import cn.ma.cei.utils.UniqueList;

import java.util.*;

public class Python3File {
    private final String filename;

    private final Python3Code code = new Python3Code();
    private final UniqueList<String, Python3Class> innerClasses = new UniqueList<>();

    public Python3File(String exchangeName) {
        this.filename = exchangeName;
    }

    public void addInnerClass(Python3Class innerClass) {
        if (innerClasses.containsKey(innerClass.getClassName())) {
            throw new CEIException("[Python3File] Cannot add duplite class");
        }
        innerClasses.put(innerClass.getClassName(), innerClass);
    }

    private void writeReference(Python3Code code) {
        Set<String> newImportList = new HashSet<>();

        innerClasses.values().forEach(value -> {
            newImportList.addAll(value.getImportList());
        });

        List<String> list = new ArrayList<>(newImportList);
        Collections.sort(list);
        list.forEach((item) -> {
            if (!item.equals(BuilderContext.NO_REF)) {
                code.appendln(item);
            }
        });
        code.endln();
        code.endln();
    }

    public void build(CEIPath folder) {
        writeReference(code);

        innerClasses.values().forEach(item -> {
            item.build();
            code.appendCode(item.getCode());
            code.endln();
        });

        CEIPath file = CEIPath.appendFile(
                folder,
                filename + ".py");
        file.write(code.toString());
    }
}
