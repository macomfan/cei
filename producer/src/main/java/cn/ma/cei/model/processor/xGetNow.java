/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.model.processor;

import cn.ma.cei.model.base.xDataProcessorItem;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author U0151316
 */
@XmlRootElement(name = "get_now")
public class xGetNow extends xDataProcessorItem {
    @XmlAttribute(name = "output")
    public String name;
    
    @XmlAttribute(name = "format")
    public String format;

    @Override
    public void customCheck() {
        super.customCheck();
        checkMemberNotNull(name, "output");
    }
}
