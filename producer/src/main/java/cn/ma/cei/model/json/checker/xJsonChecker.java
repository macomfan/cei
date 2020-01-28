package cn.ma.cei.model.json.checker;

import cn.ma.cei.model.json.xJsonType;
import cn.ma.cei.xml.XmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "json_checker")
public class xJsonChecker extends xJsonType {

    @XmlAnyElement(lax = true)
    @XmlAnyElementTypes({
            xJsonEqual.class,
            xJsonNotEqual.class})
    public List<xJsonCheckerItem> items;
}
