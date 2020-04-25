package cn.ma.cei.model;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.json.xJsonParser;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "response")
public class xResponse extends xProcedure {

    @XmlAttribute(name = "type")
    public String type;

    @XmlAttribute(name = "result")
    public String result;

    @XmlAttribute(name = "name")
    public String name;

    @Override
    public void customCheck() {
        super.customCheck();
        if (Checker.isEmpty(type) && Checker.isNull(items)) {
            CEIErrors.showXMLFailure("Response cannot be null");
        }
    }
}
