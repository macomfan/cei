package cn.ma.cei.model;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.base.xVarious;
import cn.ma.cei.model.json.xJsonBuilder;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "post_body")
public class xPostBody extends xVarious {

    @XmlAttribute(name = "value")
    public String value;

    @XmlElement(name = "json_builder")
    public xJsonBuilder jsonBuilder;
}
