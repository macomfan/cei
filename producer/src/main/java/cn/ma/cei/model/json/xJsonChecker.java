package cn.ma.cei.model.json;

import cn.ma.cei.generator.builder.IJsonCheckerBuilder;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "json_checker")
public class xJsonChecker extends xJsonWithModel {
    public IJsonCheckerBuilder.UsedFor usedFor = IJsonCheckerBuilder.UsedFor.UNDEFINED;

    @Override
    public void customCheck() {
        itemList.forEach(item -> item.optional = true);
    }
}
