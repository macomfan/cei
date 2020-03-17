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
@XmlRootElement(name = "combine_query_string")
public class xCombineQueryString extends xAuthenticationItem {

    @XmlAttribute(name = "output")
    public String output;
    
    @XmlAttribute(name = "sort")
    public String sort;
    
    @XmlAttribute(name = "separator")
    public String separator;

    @Override
    public void customCheck() {
        super.customCheck();
        checkMemberNotNull(output, "output");
    }
}
