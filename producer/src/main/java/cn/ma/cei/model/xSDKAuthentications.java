package cn.ma.cei.model;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.restful.xAuthentication;
import cn.ma.cei.model.websocket.xWSAuthentication;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "authentications")
public class xSDKAuthentications extends xElement {
//    @XmlElement(name = "restful")
//    public List<xAuthentication> restfulList;
//
//    @XmlElement(name = "websocket")
//    public List<xWSAuthentication> webSocketList;
}
