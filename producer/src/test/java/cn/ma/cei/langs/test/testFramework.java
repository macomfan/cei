package cn.ma.cei.langs.test;

import cn.ma.cei.generator.Language;
import cn.ma.cei.generator.builder.IExchangeBuilder;
import cn.ma.cei.generator.builder.IFramework;
import cn.ma.cei.langs.java.JavaDescriptionConverter;
import cn.ma.cei.generator.naming.IDescriptionConverter;

public class testFramework implements IFramework {
    @Override
    public Language getLanguage() {
        return new Language("test", "test");
    }

    @Override
    public IExchangeBuilder createExchangeBuilder() {
        return new testExchangeBuilder();
    }

    @Override
    public IDescriptionConverter getDescriptionConverter() {
        return new JavaDescriptionConverter();
    }
}
