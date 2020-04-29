package cn.ma.cei.model.websocket;

import cn.ma.cei.model.processor.xWebSocketCallback;
import cn.ma.cei.model.processor.xWebSocketSend;
import cn.ma.cei.model.xProcedure;
import cn.ma.cei.xml.CEIXmlAnyElementTypesExtension;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "system_callback")
@CEIXmlAnyElementTypesExtension(fieldName = "items",
        classes = {xWebSocketSend.class, xWebSocketCallback.class})
public class xWSSystemCallback extends xProcedure {

    @XmlAttribute(name = "callback")
    public String callback;
}
