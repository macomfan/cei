package cn.ma.cei.model;

import cn.ma.cei.model.base.xElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "procedures")
public class xCustomProcedures extends xElement {
    @XmlElement(name = "function")
    public List<xFunction> functions;
}
