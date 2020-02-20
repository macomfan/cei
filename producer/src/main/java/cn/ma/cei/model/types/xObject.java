package cn.ma.cei.model.types;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.finalizer.Alias;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.model.base.xReferable;

import javax.xml.bind.annotation.XmlRootElement;

@Alias(name = "Model")
@XmlRootElement(name = "object")
public class xObject extends xReferable {
    @Override
    public VariableType getType() {
        if (model == null || model.equals("")) {
            throw new CEIException("refer is null");
        }
        return BuilderContext.variableType(model);
    }

}
