package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.json.xJsonChecker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "trigger")
public class xTrigger extends xElement {
    @XmlElement(name = "json_checker")
    public xJsonChecker jsonChecker;
}
