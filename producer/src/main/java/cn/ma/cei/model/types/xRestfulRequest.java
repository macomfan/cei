package cn.ma.cei.model.types;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.buildin.RestfulRequest;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "restful_request")
public class xRestfulRequest extends xType {
    @Override
    public VariableType getType() {
        return RestfulRequest.getType();
    }
}
