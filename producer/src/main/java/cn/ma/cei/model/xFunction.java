package cn.ma.cei.model;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.base.xType;
import cn.ma.cei.model.types.*;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "function")
public class xFunction extends xElement {
    @XmlAttribute(name = "name", required = true)
    public String name;

    @XmlElementWrapper(name = "inputs")
    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({
            xString.class,
            xBoolean.class,
            xInt.class,
            xDecimal.class,
            xRestfulOptions.class,
            xRestfulRequest.class})
    public List<xType> inputList;

    @XmlAttribute(name = "return")
    public String procedureReturn;

    @XmlElement(name = "implementation")
    public xProcedure implementation;
}
