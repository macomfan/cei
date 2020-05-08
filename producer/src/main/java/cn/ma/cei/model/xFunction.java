package cn.ma.cei.model;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.model.base.xElementWithInputs;
import cn.ma.cei.model.types.xRestfulOptions;
import cn.ma.cei.model.types.xRestfulRequest;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.xml.CEIXmlAnyElementTypesExtension;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "function")
@CEIXmlAnyElementTypesExtension(fieldName = "inputList", classes = {
        xRestfulOptions.class,
        xRestfulRequest.class,
        // TODO add xWebSocketOptions.class
})
public class xFunction extends xElementWithInputs {
    @XmlAttribute(name = "name", required = true)
    public String name;

    @XmlAttribute(name = "return")
    public String procedureReturn;

    @XmlElement(name = "implementation")
    public xProcedure implementation;

    @Override
    public void customCheck() {
        super.customCheck();
        if (Checker.isEmpty(name)) {
            CEIErrors.showXMLFailure("name cannot be empty in function file: %s", filename);
        }
        if (implementation == null || Checker.isNull(implementation.items)) {
            CEIErrors.showXMLFailure("No any implementation is defined for function: %d", name);
        }
    }
}
