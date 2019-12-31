/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.model.types;

import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.model.base.xType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author u0151316
 */
@XmlRootElement(name = "long")
public class xLong extends xType {

    public final static String typeName = "long";
    
    public final static xLong inst = new xLong();

    @Override
    public VariableType getType() {
        return VariableFactory.variableType(typeName);
    }
}
