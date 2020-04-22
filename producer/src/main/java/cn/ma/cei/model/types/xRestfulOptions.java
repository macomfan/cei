package cn.ma.cei.model.types;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "restful_options")
public class xRestfulOptions extends xType {
    @Override
    public VariableType getType() {
        return RestfulOptions.getType();
    }
}
