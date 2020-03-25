/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.model.string;

import cn.ma.cei.model.base.xDataProcessorItem;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author u0151316
 */
@XmlRootElement(name = "add_string_item")
public class xAddStringItem extends xDataProcessorItem {

    @XmlAttribute(name = "input", required = true)
    public String input;

    @Override
    public void customCheck() {
        super.customCheck();
        checkMemberNotNull(input, "input");
    }
}
