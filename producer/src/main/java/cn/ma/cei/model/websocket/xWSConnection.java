package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.utils.Checker;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "connection_websocket")
@XmlRootElement(name = "connection")
public class xWSConnection extends xElement {
    @XmlAttribute(name = "url", required = true)
    public String url;

    @XmlAttribute(name = "timeout_s")
    public Integer timeout;

    @XmlElement(name = "open")
    public xWSOpen open;

    @XmlElement(name = "close")
    public xWSClose close;

    @Override
    public void customCheck() {
        super.customCheck();
        if (!Checker.isEmpty(url)) {
            url = url.replace('\\', '/');
            if (url.endsWith("/")) {
                url = url.substring(0, url.length() - 1);
            }
        }
    }
}
