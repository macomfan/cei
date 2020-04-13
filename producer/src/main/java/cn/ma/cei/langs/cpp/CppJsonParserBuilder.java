/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.Variable;

/**
 *
 * @author U0151316
 */
public class CppJsonParserBuilder implements IJsonParserBuilder {

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
    public IJsonCheckerBuilder createJsonCheckerBuilder() {
        return null;
    }

    @Override
    public void assignJsonStringArray(Variable to, Variable jsonObject, Variable itemName) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void assignJsonDecimalArray(Variable to, Variable jsonObject, Variable itemName) {

    }

    @Override
    public void assignJsonBooleanArray(Variable to, Variable jsonObject, Variable itemName) {

    }

    @Override
    public void assignJsonIntArray(Variable to, Variable jsonObject, Variable itemName) {

    }

    @Override
    public void getJsonArray(Variable jsonWrapperObject, Variable jsonObject, Variable itemName) {

    }

    @Override
    public void defineJsonObject(Variable jsonObject, Variable parentJsonObject, Variable itemName) {

    }

    @Override
    public void assignModel(Variable to, Variable model) {

    }

    @Override
    public void startJsonObjectArray(Variable eachItemJsonObject, Variable jsonObject) {

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
