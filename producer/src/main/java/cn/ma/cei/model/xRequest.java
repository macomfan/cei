package cn.ma.cei.model;

import cn.ma.cei.model.base.xElement;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
public class xRequest extends xElement {

    @XmlAttribute(name = "method")
    public String method;

    @XmlAttribute(name = "target")
    public String target;

    @XmlAttribute(name = "signature")
    public String signature;

    @XmlElementWrapper(name = "headers")
    @XmlElement(name = "header")
    public List<xHeader> headers;

    @XmlElementWrapper(name = "query_strings")
    @XmlElement(name = "query")
    public List<xQuery> queryStrings;

    @XmlElement(name = "post_body")
    public xPostBody postBody;
}
