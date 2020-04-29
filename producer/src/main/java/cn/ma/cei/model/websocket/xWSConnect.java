package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElementWithInputs;
import cn.ma.cei.model.xPreProcessor;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "connect")
public class xWSConnect extends xElementWithInputs {

    @XmlAttribute
    public String target;

    @XmlElement(name = "pre_processor")
    public xPreProcessor preProcessor;
}
