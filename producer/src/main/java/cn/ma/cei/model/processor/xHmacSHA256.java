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
 * @author u0151316
 */
@XmlRootElement(name = "hmacsha256")
public class xHmacSHA256 extends xDataProcessorItem {

    @XmlAttribute(name = "name")
    public String name;

    @XmlAttribute(name = "input")
    public String input;

    @XmlAttribute(name = "key")
    public String key;

    @Override
    public void customCheck() {
        super.customCheck();
        checkMemberNotNull(input, "input");
        checkMemberNotNull(name, "output");
        checkMemberNotNull(key, "key");
    }
}
