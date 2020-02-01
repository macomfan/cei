/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.cpp;

import cn.ma.cei.generator.builder.JsonCheckerBuilder;
import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.environment.Variable;

/**
 *
 * @author U0151316
 */
public class CppJsonParserBuilder extends JsonParserBuilder {

    @Override
    public void getJsonString(Variable to, Variable jsonObject, Variable itemName) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getJsonInteger(Variable to, Variable jsonObject, Variable itemName) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void defineModel(Variable model) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable responseVariable) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JsonCheckerBuilder createJsonCheckerBuilder() {
        return null;
    }

    @Override
    public void getJsonStringArray(Variable to, Variable jsonObject, Variable itemName) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startJsonObject(Variable jsonObject, Variable parentJsonObject, Variable itemName) {

    }

    @Override
    public void endJsonObject(Variable to, Variable model) {

    }

    @Override
    public void startJsonObjectArray(Variable eachItemJsonObject, Variable parentJsonObject, Variable itemName) {

    }

    @Override
    public void endJsonObjectArray(Variable to, Variable model) {

    }

    @Override
    public void getJsonBoolean(Variable to, Variable jsonObject, Variable itemName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getJsonDecimal(Variable to, Variable jsonObject, Variable itemName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
