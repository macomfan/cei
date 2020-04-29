package cn.ma.cei.model.websocket;

import cn.ma.cei.model.processor.xWebSocketSend;
import cn.ma.cei.model.xProcedure;
import cn.ma.cei.xml.CEIXmlAnyElementTypesExtension;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
@CEIXmlAnyElementTypesExtension(fieldName = "items",
        classes = {xWebSocketSend.class})
public class xWSMessage extends xProcedure {
}
