/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.model.json;

import cn.ma.cei.model.base.xBuilder;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;
import java.util.List;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author u0151316
 */
@XmlRootElement(name = "json_builder")
public class xJsonBuilder extends xBuilder {

    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({
        xJsonString.class,
        xJsonInteger.class,
        xJsonBoolean.class,
        xJsonDecimal.class})
    public List<xJsonType> itemList;
}
