/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.model.signature;

import cn.ma.cei.model.base.xSignatureItem;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author U0151316
 */
@XmlRootElement(name = "add_query_string")
public class xAddQueryString extends xSignatureItem {
    @XmlAttribute(name = "key")
    public String key;

    @XmlAttribute(name = "value")
    public String value;

    @Override
    public void customCheck() {
        super.customCheck();
        checkMemberNotNull(key, "key");
        checkMemberNotNull(value, "value");
    }
}
