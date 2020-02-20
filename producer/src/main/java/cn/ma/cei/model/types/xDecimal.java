/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.model.types;

import cn.ma.cei.finalizer.Alias;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.model.base.xType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author u0151316
 */
@Alias(name = "Decimal")
@XmlRootElement(name = "decimal")
public class xDecimal extends xType {

    public final static String typeName = "decimal";

    public final static xDecimal inst = new xDecimal();
    
    @Override
    public VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
