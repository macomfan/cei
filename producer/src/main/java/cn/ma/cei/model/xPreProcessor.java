package cn.ma.cei.model;

import cn.ma.cei.model.processor.xGZip;
import cn.ma.cei.xml.CEIXmlAnyElementTypesExtension;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pre_processor")
@CEIXmlAnyElementTypesExtension(fieldName = "items", classes = {xGZip.class})
public class xPreProcessor extends xProcedure {
}
