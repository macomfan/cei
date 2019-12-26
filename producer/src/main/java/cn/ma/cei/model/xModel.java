package cn.ma.cei.model;

import cn.ma.cei.finalizer.IDependenceNode;
import cn.ma.cei.model.base.xType;
import cn.ma.cei.model.types.*;
import cn.ma.cei.model.types.xObject;
import cn.ma.cei.xml.XmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "model")
public class xModel implements IDependenceNode {
    @XmlAttribute(name = "name")
    public String name;

    @XmlAnyElement(lax = true)
    @XmlAnyElementTypes({
        xString.class,
        xBoolean.class,
        xInt.class,
        xLong.class,
        xDecimal.class,
        xObject.class,
        xObjectList.class,
        xStringList.class})
    public List<xType> memberList;

    @Override
    public String getIdentifier() {
        return name;
    }
}
