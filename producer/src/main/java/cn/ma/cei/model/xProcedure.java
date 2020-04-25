package cn.ma.cei.model;

import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.json.xJsonBuilder;
import cn.ma.cei.model.json.xJsonParser;
import cn.ma.cei.model.processor.*;
import cn.ma.cei.model.string.xStringBuilder;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "procedure")
public class xProcedure extends xElement {
    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({
            xGZip.class,
            xBase64.class,
            xHmacSHA256.class,
            xURLEscape.class,
            xGetNow.class,
            xJsonBuilder.class,
            xJsonParser.class,
            xStringBuilder.class,
            xAddQueryString.class,
            xCombineQueryString.class,
            xGetRequestInfo.class,
            xInvoke.class})
    public List<xDataProcessorItem> items;
}
