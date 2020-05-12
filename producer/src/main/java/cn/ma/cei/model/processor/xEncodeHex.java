package cn.ma.cei.model.processor;

import cn.ma.cei.model.base.xDataProcessorItem;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "encode_hex")
public class xEncodeHex extends xDataProcessorItem {
    @XmlAttribute(name = "name")
    public String name;

    @XmlAttribute(name = "input")
    public String input;

    @Override
    public void customCheck() {
        super.customCheck();
        checkMemberNotNull(input, "input");
        checkMemberNotNull(name, "output");
    }
}
