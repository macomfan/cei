/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang;

import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.builder.ResponseBuilder;
import cn.ma.cei.generator.langs.golang.tools.GoMethod;

/**
 *
 * @author U0151316
 */
public class GoResponseBuilder extends ResponseBuilder {

    private GoMethod method;

    public GoResponseBuilder(GoMethod method) {
        this.method = method;
    }

    @Override
    public JsonParserBuilder getJsonParserBuilder() {
        return new GoJsonParserBuilder(method);
    }

}
