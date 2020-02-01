/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.cpp;

import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.builder.ResponseBuilder;

/**
 *
 * @author U0151316
 */
public class CppResponseBuilder extends ResponseBuilder {

    @Override
    public JsonParserBuilder getJsonParserBuilder() {
        return new CppJsonParserBuilder();
    }

}
