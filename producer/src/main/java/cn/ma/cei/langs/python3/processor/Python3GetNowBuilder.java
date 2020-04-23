package cn.ma.cei.langs.python3.processor;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IGetNowBuilder;
import cn.ma.cei.langs.python3.tools.Python3Method;

public class Python3GetNowBuilder implements IGetNowBuilder {

    Python3Method method;

    public Python3GetNowBuilder(Python3Method method) {
        this.method = method;
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
                        res.append("%Y");
                        commandString = "";
                        break;
                    case "%M":
                        res.append("%m");
                        commandString = "";
                        break;
                    case "%D":
                        res.append("%d");
                        commandString = "";
                        break;
                    case "%h":
                        res.append("#H");
                        commandString = "";
                        break;
                    case "%m":
                        res.append("%M");
                        commandString = "";
                        break;
                    case "%s":
                        res.append("%S");
                        commandString = "";
                        break;
                    case "%ms":
                        res.append("%SSS%");
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
        } else {
            if (normalString.length() > 0) {
                res.append('\'');
                res.append(normalString.toString());
                res.append('\'');
            }
        }
        return res.toString();
    }


    @Override
    public String convertToStringFormat(String format) {
        if ("Unix_s".equals(format) || "Unix_ms".equals(format)) {
            return format;
        } else {
            StringBuilder javaTimeFormatSting = new StringBuilder();
            String[] items = format.split("\\%");
            for (int i = 0; i < items.length; i++) {
                if (i == 0) {
                    if (!"".equals(items[i])) {
                        javaTimeFormatSting.append('\'');
                        javaTimeFormatSting.append(items[i]);
                        javaTimeFormatSting.append('\'');
                    }
                } else {
                    javaTimeFormatSting.append(processSingleTimeFormatSyntax("%" + items[i]));
                }
            }
            return javaTimeFormatSting.toString();
        }
    }

    @Override
    public void getNow(Variable output, Variable format) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.get_now", format));
    }
}
