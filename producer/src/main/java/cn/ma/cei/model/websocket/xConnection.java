package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.base.xType;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xDecimal;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlType(name = "connection_websocket")
@XmlRootElement(name = "connection")
public class xConnection extends xElement {

    @XmlElementWrapper(name = "inputs")
    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({xString.class, xBoolean.class, xInt.class, xDecimal.class})
    public List<xType> inputList;

    @XmlElement(name = "on_connect")
    public xOnConnect onConnect;

    @XmlElement(name = "url")
    public xUrl url;

    @XmlAttribute(name="timeout_s")
    public Integer timeout;


}
