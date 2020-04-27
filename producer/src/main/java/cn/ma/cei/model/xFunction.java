package cn.ma.cei.model;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.base.xElementWithInputs;
import cn.ma.cei.model.base.xType;
import cn.ma.cei.model.types.*;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;
import cn.ma.cei.xml.CEIXmlAnyElementTypesExtension;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "function")
@CEIXmlAnyElementTypesExtension(fieldName = "inputList", classes = {
        xRestfulOptions.class,
        xRestfulRequest.class
})
public class xFunction extends xElementWithInputs {
    @XmlAttribute(name = "name", required = true)
    public String name;

//    @XmlElementWrapper(name = "inputs")
//    @XmlAnyElement(lax = true)
//    @CEIXmlAnyElementTypes(classes = {
//            xString.class,
//            xBoolean.class,
//            xInt.class,
//            xDecimal.class,
//})
//    public List<xType> inputList;

    @XmlAttribute(name = "return")
    public String procedureReturn;

    @XmlElement(name = "implementation")
    public xProcedure implementation;
}
