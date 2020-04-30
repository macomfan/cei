package cn.ma.cei.model.json;

import cn.ma.cei.generator.builder.IJsonCheckerBuilder;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "json_checker")
public class xJsonChecker extends xJsonWithModel {
    public IJsonCheckerBuilder.UsedFor usedFor = IJsonCheckerBuilder.UsedFor.UNDEFINED;
}
