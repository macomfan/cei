package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.builder.ResponseBuilder;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;

public class JavaResponseBuilder extends ResponseBuilder {
    private JavaMethod method;

    public JavaResponseBuilder(JavaMethod method) {
        this.method = method;
    }

    @Override
    public JsonParserBuilder getJsonParserBuilder() {
        return new JavaJsonParserBuilder(method);
    }
}
