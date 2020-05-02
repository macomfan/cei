/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;

/**
 *
 * @author U0151316
 */
public class CppJsonParserBuilder implements IJsonParserBuilder {

    @Override
    public void getJsonString(Variable value, Variable jsonObject, Variable key, boolean optional) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getJsonInteger(Variable value, Variable jsonObject, Variable key, boolean optional) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void defineModel(Variable model) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable stringVariable) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IJsonCheckerBuilder createJsonCheckerBuilder() {
        return null;
    }

    @Override
    public void assignJsonStringArray(Variable value, Variable jsonObject, Variable key, boolean optional) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void assignJsonDecimalArray(Variable value, Variable jsonObject, Variable key, boolean optional) {

    }

    @Override
    public void assignJsonBooleanArray(Variable value, Variable jsonObject, Variable key, boolean optional) {

    }

    @Override
    public void assignJsonIntArray(Variable value, Variable jsonObject, Variable key, boolean optional) {

    }

    @Override
    public void getJsonArray(Variable jsonWrapperObject, Variable jsonObject, Variable key) {

    }

    @Override
    public void defineJsonObject(Variable jsonObject, Variable parentJsonObject, Variable key, boolean optional) {

    }

    @Override
    public void defineJsonArray(Variable jsonObject, Variable parentJsonObject, Variable key) {

    }

    @Override
    public void assignModel(Variable to, Variable model) {

    }

    @Override
    public void startJsonObjectArray(Variable eachItemJsonObject, Variable jsonObject) {

    }

    @Override
    public void endJsonObjectArray(Variable value, Variable model) {

    }

    @Override
    public void getJsonBoolean(Variable value, Variable jsonObject, Variable key, boolean optional) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getJsonDecimal(Variable value, Variable jsonObject, Variable key, boolean optional) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
