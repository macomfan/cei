package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.base.xType;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlType(name = "interface_websocket")
@XmlRootElement(name = "interface")
public class xInterface extends xElement {
    @XmlAttribute(name = "name", required = true)
    public String name;

    @XmlElementWrapper(name = "inputs")
    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({xString.class, xBoolean.class, xInt.class})
    public List<xType> inputList;

    @XmlElement(name = "send")
    public xSend send;

    @XmlElement(name = "callback")
    public List<xCallback> callbacks;
}
