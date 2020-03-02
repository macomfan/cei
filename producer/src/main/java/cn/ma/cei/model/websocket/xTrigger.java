package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.json.xJsonParser;

import javax.xml.bind.annotation.XmlElement;

public class xTrigger extends xElement {
    @XmlElement(name = "json_parser")
    public xJsonParser jsonParser;
}
