/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.model.json;

import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author u0151316
 */
@XmlRootElement(name = "json_builder")
public class xJsonBuilder extends xDataProcessorItem {

    @XmlAttribute(name = "name")
    public String name;

    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes(classes = {
            xJsonValue.class,
            xJsonString.class,
            xJsonInteger.class,
            xJsonBoolean.class,
            xJsonDecimal.class,
    xJsonObject.class})
    public List<xJsonType> itemList;
}
