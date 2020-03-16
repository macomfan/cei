package cn.ma.cei.model;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.restful.xRestful;
import cn.ma.cei.model.websocket.xWebSocket;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "clients")
public class xSDKClients extends xElement {
    @XmlElement(name = "restful")
    public List<xRestful> restfulList;

    @XmlElement(name = "websocket")
    public List<xWebSocket> webSocketList;
}
