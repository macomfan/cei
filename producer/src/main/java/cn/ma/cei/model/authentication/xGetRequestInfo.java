/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.model.authentication;

import cn.ma.cei.model.base.xDataProcessorItem;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author u0151316
 */
@XmlRootElement(name = "get_request_info")
public class xGetRequestInfo extends xDataProcessorItem {
    @XmlAttribute(name = "info")
    public String info;

    @XmlAttribute(name = "name")
    public String name;
    
    @XmlAttribute(name = "convert")
    public String convert;

    @Override
    public void customCheck() {
        super.customCheck();
        checkMemberNotNull(info, "info");
        checkMemberNotNull(name, "name");
    }
}
