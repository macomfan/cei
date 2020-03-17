package cn.ma.cei.model.base;

import cn.ma.cei.model.xAttribute;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "")
public class xAttributeExtension extends xElement {

    @XmlElement(name = "attribute")
    public List<xAttribute> attributes;

    public xAttribute getAttributeByName(String name) {
        if (attributes == null) return null;
        xAttribute res = null;
        for (xAttribute value : attributes) {
            if (value.forAttribute != null && value.forAttribute.equals(name)) {
                res = value;
            }
        }
        return res;
    }
}
