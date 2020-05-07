package cn.ma.cei.model.json;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "json_object")
public class xJsonObject extends xJsonWithModel {
    @Override
    public void customCheck() {
        super.customCheck();
        if (optional) {
            itemList.forEach(item -> item.optional = true);
        }
    }
}
