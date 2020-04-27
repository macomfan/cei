package cn.ma.cei.model;

import cn.ma.cei.finalizer.Alias;
import cn.ma.cei.finalizer.IDependenceNode;
import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.base.xType;
import cn.ma.cei.model.types.*;
import cn.ma.cei.model.types.xObject;
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
}
