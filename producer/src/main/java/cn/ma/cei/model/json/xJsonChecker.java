package cn.ma.cei.model.json;

import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "json_checker")
public class xJsonChecker extends xDataProcessorItem {

    public IJsonCheckerBuilder.UsedFor usedFor = IJsonCheckerBuilder.UsedFor.UNDEFINED;

    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({
            xJsonEqual.class,
            xJsonNotEqual.class})
    public List<xJsonCheckerItem> items;
}
