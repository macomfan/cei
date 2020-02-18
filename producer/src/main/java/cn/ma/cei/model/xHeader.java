/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.model;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author u0151316
 */
@XmlRootElement(name = "header")
public class xHeader extends xElement {
    
    @XmlAttribute(name = "tag")
    public String tag;

    @XmlAttribute(name = "value")
    public String value;

    @Override
    public void customCheck() {
        super.customCheck();
        checkMemberNotNull(tag, "tag");
        checkMemberNotNull(value, "value");
    }
}
