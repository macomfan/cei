/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.model.authentication;

import cn.ma.cei.model.base.xAuthenticationItem;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author u0151316
 */
@XmlRootElement(name = "base64")
public class xBase64 extends xAuthenticationItem {

    @XmlAttribute(name = "output")
    public String output;

    @XmlAttribute(name = "input")
    public String input;

    @Override
    public void customCheck() {
        super.customCheck();
        checkMemberNotNull(input, "input");
        checkMemberNotNull(output, "output");
    }
}
