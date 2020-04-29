package cn.ma.cei.model.restful;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.utils.Checker;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "connection_restful")
@XmlRootElement(name = "connection")
public class xConnection extends xElement {

    @XmlAttribute(name = "url", required = true)
    public String url;

    @XmlAttribute(name = "timeout_s")
    public Integer timeout;

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
