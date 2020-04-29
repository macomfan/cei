package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElementWithInputs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "close")
public class xWSClose extends xElementWithInputs {
    @XmlElement(name = "on_close")
    public xWSSystemCallback onClose;
}
