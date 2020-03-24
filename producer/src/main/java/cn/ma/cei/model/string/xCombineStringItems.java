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
@XmlRootElement(name = "combine_string_items")
public class xCombineStringItems extends xDataProcessorItem {

    @XmlAttribute(name = "separator")
    public String separator;

    @Override
    public void customCheck() {
        super.customCheck();
    }
}
