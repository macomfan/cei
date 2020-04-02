package cn.ma.cei.model;

import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.json.xJsonBuilder;
import cn.ma.cei.model.processor.xBase64;
import cn.ma.cei.model.processor.xGetNow;
import cn.ma.cei.model.processor.xHmacSHA256;
import cn.ma.cei.model.processor.xURLEscape;
import cn.ma.cei.model.string.xStringBuilder;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "procedure")
public class xProcedure extends xDataProcessorItem {
    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({
            xBase64.class,
            xHmacSHA256.class,
            xURLEscape.class,
            xGetNow.class,
            xJsonBuilder.class,
            xStringBuilder.class})
    public List<xDataProcessorItem> items;
}
