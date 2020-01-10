/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang;

import cn.ma.cei.generator.builder.ExchangeBuilder;
import cn.ma.cei.generator.builder.Framework;
import cn.ma.cei.generator.naming.IDescriptionConverter;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author U0151316
 */
public class GoFramework extends Framework {

    @Override
    public String getFrameworkName() {
        return "cei_golang";
    }

    @Override
    public ExchangeBuilder getExchangeBuilder() {
        return new GoExchangeBuilder();
    }

    @Override
    public IDescriptionConverter getDescriptionConverter() {
        return new GoDescriptionConverter();
    }

    @Override
    public Set<String> getKeywords() {
        Set<String> keywords = new HashSet<>();
        keywords.add("var");
        keywords.add("const");
        keywords.add("package");
        keywords.add("import");
        keywords.add("func");
        keywords.add("return");
        keywords.add("defer");
        keywords.add("type");
        keywords.add("struct");
        keywords.add("interface");
        keywords.add("string");
        keywords.add("int");
        keywords.add("uint");
        keywords.add("int8");
        keywords.add("int16");
        keywords.add("int32");
        keywords.add("int64");
        keywords.add("uint8");
        keywords.add("uint16");
        keywords.add("uint32");
        keywords.add("uint64");
        keywords.add("float32");
        keywords.add("float64");
        keywords.add("this");
        keywords.add("self");
        keywords.add("byte");
        return keywords;
    }
    
}
