package cn.ma.cei.model;

import cn.ma.cei.model.base.xStandalone;
import cn.ma.cei.model.restful.xRestful;
import cn.ma.cei.model.websocket.xWebSocket;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "clients")
public class xSDKClients extends xStandalone<xSDKClients> {
    @XmlElement(name = "restful")
    public List<xRestful> restfulList;

    @XmlElement(name = "websocket")
    public List<xWebSocket> webSocketList;

    public void merge(xSDKClients clients) {
        restfulList = mergeList(restfulList, clients.restfulList);
        webSocketList = mergeList(webSocketList, clients.webSocketList);
    }
}
