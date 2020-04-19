package cn.ma.cei.model.json;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;

public abstract class xJsonType extends xElement {
    @XmlAttribute(name = "copy")
    public String copy;

    @XmlAttribute(name = "key")
    public String key;

    @XmlAttribute(name = "value")
    public String value;

    @Override
    public void customCheck() {
        super.customCheck();
        if (copy != null && (key != null || value != null)) {
            CEIErrors.showXMLFailure(this, " Key, Value cannot exist with copy.");
        } else if (copy != null) {
            key = copy;
            value = "{" + copy + "}";
            copy = null;
        }
    }
}
