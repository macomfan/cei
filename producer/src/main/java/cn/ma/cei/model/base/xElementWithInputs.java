package cn.ma.cei.model.base;

import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xDecimal;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

public class xElementWithInputs extends xElement {
    @XmlElementWrapper(name = "inputs")
    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes(classes = {
            xString.class,
            xBoolean.class,
            xInt.class,
            xDecimal.class})
    public List<xType> inputList;
}
