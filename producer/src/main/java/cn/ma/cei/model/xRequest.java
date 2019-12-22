package cn.ma.cei.model;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
public class xRequest {
    @XmlAttribute(name = "method")
    public String method;

    @XmlAttribute
    public String target;

    @XmlElement(name = "query_strings")
    public xQueryStrings queryStrings;

    @XmlElement(name = "post_body")
    public xPostBody postBody;
}
