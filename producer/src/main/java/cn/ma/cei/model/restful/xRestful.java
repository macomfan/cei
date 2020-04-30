package cn.ma.cei.model.restful;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "restful")
public class xRestful extends xElement {

    @XmlAttribute(name = "name")
    public String name;

//    @XmlAttribute(name = "successful_code")
//    public String successfulCode;
//
//    @XmlAttribute(name = "timeout")
//    public Integer timeout;

    @XmlElement(name = "connection")
    public xConnection connection;

    @XmlElementWrapper(name = "interfaces")
    @XmlElement(name = "interface")
    public List<xInterface> interfaceList;

    public Set<String> getInterfaceSet() {
        Set<String> res = new HashSet<>();
        interfaceList.forEach(intf -> res.add(intf.name));
        return res;
    }
}
