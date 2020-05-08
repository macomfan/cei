package cn.ma.cei.model;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.finalizer.Alias;
import cn.ma.cei.finalizer.IDependenceNode;
import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.base.xType;
import cn.ma.cei.model.types.*;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Alias(name = "xModel")
@XmlRootElement(name = "model")
public class xModel extends xElement implements IDependenceNode {

    @XmlAttribute(name = "name", required = true)
    public String name;

    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes(classes = {
            xString.class,
            xBoolean.class,
            xInt.class,
            xDecimal.class,
            xObject.class,
            xObjectArray.class,
            xStringArray.class,
            xDecimalArray.class,
            xBooleanArray.class})
    public List<xType> memberList;

    @Override
    public String getIdentifier() {
        return name;
    }

    @Override
    public void customCheck() {
        super.customCheck();
        if (Checker.isEmpty(name)) {
            CEIErrors.showXMLFailure("name cannot be empty in model file: %s", filename);
        }
    }
}
