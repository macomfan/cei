package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.buildin.TheStream;
import cn.ma.cei.model.processor.xGZip;
import cn.ma.cei.model.types.xString;

public class BuildGZip extends DataProcessorBase<xGZip> {
    @Override
    public Variable build(xGZip item, IDataProcessorBuilder builder) {
        Variable input = queryInputVariable(item.input, "{msg}", TheStream.getType());
        Variable output = createUserVariable(xString.inst.getType(), item.name);
        if (item.type.equals(xGZip.COMPRESS)) {
            CEIErrors.showCodeFailure(BuildGZip.class, "gzip compress is not supporting");
        } else if (item.type.equals(xGZip.DECOMPRESS)) {
            builder.gzip(output, input);
        }
        return output;
    }

    @Override
    public VariableType returnType(xGZip item) {
        return xString.inst.getType();
    }

    @Override
    public String resultVariableName(xGZip item) {
        return item.name;
    }
}
