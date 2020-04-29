package cn.ma.cei.model.websocket;

import cn.ma.cei.model.processor.xWebSocketSend;
import cn.ma.cei.model.xProcedure;
import cn.ma.cei.xml.CEIXmlAnyElementTypesExtension;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "handler")
@CEIXmlAnyElementTypesExtension(fieldName = "items", classes = {xWebSocketSend.class})
public class xWSHandler extends xProcedure {
}
