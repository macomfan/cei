package cn.ma.cei.model.base;

import cn.ma.cei.model.xProcedure;

import javax.xml.bind.annotation.XmlElement;


public class xItemWithProcedure extends xElement {
    @XmlElement(name = "procedure")
    public xProcedure procedure;
}
