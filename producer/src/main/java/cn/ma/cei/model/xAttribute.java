package cn.ma.cei.model;

import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.json.xJsonBuilder;
import cn.ma.cei.model.processor.xGetNow;
import cn.ma.cei.model.string.xStringBuilder;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "attribute")
public class xAttribute extends xElement {

    @XmlAttribute(name = "for")
    public String forAttribute;

    @XmlAttribute(name = "result")
    public String result;

    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({
            xGetNow.class,
            xJsonBuilder.class,
            xStringBuilder.class})
    public List<xDataProcessorItem> items;

//
//    @XmlElement(name = "json_builder")
//    public xJsonBuilder jsonBuilder;
//
//    @XmlElement(name = "string_builder")
//    public xStringBuilder stringBuilder;
}
