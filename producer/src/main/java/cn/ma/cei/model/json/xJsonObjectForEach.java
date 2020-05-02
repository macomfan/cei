package cn.ma.cei.model.json;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "json_object_for_each")
public class xJsonObjectForEach extends xJsonWithModel {
    @Override
    public void customCheck() {
        super.customCheck();
        if (optional) {
            itemList.forEach(item -> item.optional = true);
        }
    }
}
