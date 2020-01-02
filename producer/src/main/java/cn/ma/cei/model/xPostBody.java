package cn.ma.cei.model;

import cn.ma.cei.model.json.xJsonBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "post_body")
public class xPostBody {

    @XmlElement(name = "json_builder")
    public xJsonBuilder jsonBuilder;
}
