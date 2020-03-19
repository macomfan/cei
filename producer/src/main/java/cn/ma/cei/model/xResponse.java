package cn.ma.cei.model;

import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.json.xJsonParser;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "response")
public class xResponse extends xElement {

    @XmlAttribute(name = "model")
    public String model;

    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({
            xJsonParser.class})
    public List<xDataProcessorItem> items;

    @Override
    public void customCheck() {
        super.customCheck();
        checkMemberNotNull(model, "model");
    }
}
