package cn.ma.cei.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "query_strings")
public class xQueryStrings {
    @XmlElement(name = "query")
    public List<xQuery> queryList;


}
