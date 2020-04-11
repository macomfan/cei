package cn.ma.cei.langs.java.processor;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IGetNowBuilder;
import cn.ma.cei.langs.java.tools.JavaMethod;


public class JavaGetNowBuilder implements IGetNowBuilder {

    JavaMethod method;

    public JavaGetNowBuilder(JavaMethod method) {
        this.method = method;
    }

    @Override
    public String convertToStringFormat(String format) {

        if ("Unix_s".equals(format) || "Unix_ms".equals(format)) {
            return format;
        } else {
            StringBuilder javaTimeFormatSting = new StringBuilder();
            String[] items = format.split("\\%");
            for (int i = 0; i<items.length; i++) {
                if (i == 0) {
                    if (!"".equals(items[i])) {
                        javaTimeFormatSting.append('\'');
                        javaTimeFormatSting.append(items[i]);
                        javaTimeFormatSting.append('\'');
                    }
                }
                else {
                    javaTimeFormatSting.append(processSingleTimeFormatSyntax("%" + items[i]));
                }
            }
            return javaTimeFormatSting.toString();
        }
    }

    @Override
    public void getNow(Variable output, Variable format) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.getNow", format));
    }



    private static String processSingleTimeFormatSyntax(String input) {
        if (input == null || input.isEmpty() || input.charAt(0) != '%') {
            return null;
        }
        StringBuilder res = new StringBuilder();
        String commandString = "%";
        StringBuilder normalString = new StringBuilder();
        for (int i = 1; i < input.length(); i++) {
            if (!commandString.isEmpty()) {
                commandString += input.charAt(i);
                switch (commandString) {
                    case "%Y":
                        res.append("yyyy");
                        commandString = "";
                        break;
                    case "%M":
                        res.append("MM");
                        commandString = "";
                        break;
                    case "%D":
                        res.append("dd");
                        commandString = "";
                        break;
                    case "%h":
                        res.append("HH");
                        commandString = "";
                        break;
                    case "%m":
                        res.append("mm");
                        commandString = "";
                        break;
                    case "%s":
                        res.append("ss");
                        commandString = "";
                        break;
                    case "%ms":
                        res.append("SSS");
                        commandString = "";
                }
            } else {
                normalString.append(input.charAt(i));
            }
        }
        if (!commandString.isEmpty()) {
            // TODO
            // Cannot process
            System.err.println("Cannot support " + commandString);
        }
        else {
            if (normalString.length() > 0) {
                res.append('\'');
                res.append(normalString.toString());
                res.append('\'');
            }
        }
        return res.toString();
    }
}
