package cn.ma.cei.model;

import cn.ma.cei.model.base.xElementWithInputs;
import cn.ma.cei.model.types.xRestfulOptions;
import cn.ma.cei.model.types.xRestfulRequest;
import cn.ma.cei.xml.CEIXmlAnyElementTypesExtension;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
