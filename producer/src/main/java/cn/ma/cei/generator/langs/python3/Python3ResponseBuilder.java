/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.builder.ResponseBuilder;
import cn.ma.cei.generator.langs.python3.tools.Python3Method;

/**
 *
 * @author U0151316
 */
public class Python3ResponseBuilder extends ResponseBuilder {

    private Python3Method method;

    public Python3ResponseBuilder(Python3Method method) {
        this.method = method;
    }

    @Override
    public JsonParserBuilder getJsonParserBuilder() {
        return new Python3JsonParserBuilder(method);
    }
    
}
