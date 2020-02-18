package cn.ma.cei.model;

import cn.ma.cei.model.base.xVarious;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "query")
public class xQuery extends xVarious {

    @XmlAttribute(name = "key")
    public String key;

    @XmlAttribute(name = "value")
    public String value;

    @XmlAttribute(name = "copy")
    public String copy;
}
