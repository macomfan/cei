package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IGetNowBuilder;
import cn.ma.cei.model.processor.xGetNow;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.Checker;

public class BuildGetNow extends DataProcessorBase<xGetNow> {

    @Override
    public Variable build(xGetNow item, IDataProcessorBuilder builder) {
        IGetNowBuilder getNowBuilder = Checker.checkNull(builder.createGetNowBuilder(), builder, "GetNowBuilder");
        Variable output = createUserVariable(xString.inst.getType(), item.name);
        String convertedFormatString = "";
        if (!Checker.isEmpty(item.format)) {
            convertedFormatString = getNowBuilder.convertToStringFormat(item.format);
        }
        if (convertedFormatString == null) {
            CEIErrors.showCodeFailure(getNowBuilder.getClass(), "Format string is empty");
        }
        Variable format = BuilderContext.createStringConstant(convertedFormatString);
        getNowBuilder.getNow(output, format);
        return output;
    }

    @Override
    public VariableType returnType(xGetNow item) {
        return xString.inst.getType();
    }

    @Override
    public String resultVariableName(xGetNow item) {
        return item.name;
    }
}
