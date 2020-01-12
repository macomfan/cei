package cn.ma.cei.model.base;


import cn.ma.cei.exception.BuildTracer;
import cn.ma.cei.xml.XmlElementBase;

import javax.xml.bind.annotation.XmlAttribute;

@XmlElementBase
public abstract class xElement {
    @XmlAttribute(name = "id")
    public String id;
    
    public void startBuilding() {
        BuildTracer.startBuilding(this);
    }
    public void endBuilding() {
        BuildTracer.endBuilding();
    }
    
    public void check() {
        
    }
}
