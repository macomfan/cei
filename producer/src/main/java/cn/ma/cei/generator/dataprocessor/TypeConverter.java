package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.buildin.*;
import cn.ma.cei.model.types.*;
import cn.ma.cei.utils.NormalMap;
import cn.ma.cei.utils.SecondLevelMap;

public class TypeConverter {
    // From - to - converter
    public static final SecondLevelMap<VariableType, VariableType, Func> converterMap = new SecondLevelMap<>();
    public static final NormalMap<VariableType, Func> nativeConvertMap = new NormalMap<>();

    @FunctionalInterface
    interface Func {
        Variable convert(IDataProcessorBuilder builder, Variable variable);
    }

    static {
        converterMap.put(JsonWrapper.getType(), xString.inst.getType(), (IDataProcessorBuilder::convertJsonWrapperToString));
        converterMap.put(StringWrapper.getType(), xString.inst.getType(), (IDataProcessorBuilder::convertStringWrapperToString));
        converterMap.put(StringWrapper.getType(), xStringArray.inst.getType(), (IDataProcessorBuilder::convertStringWrapperToArray));

        converterMap.put(xInt.inst.getType(), xString.inst.getType(), (IDataProcessorBuilder::convertIntToString));
        converterMap.put(xBoolean.inst.getType(), xString.inst.getType(), (IDataProcessorBuilder::convertBooleanToString));
        converterMap.put(xDecimal.inst.getType(), xString.inst.getType(), (IDataProcessorBuilder::convertDecimalToString));
        converterMap.put(TheStream.getType(), xString.inst.getType(), (IDataProcessorBuilder::convertStreamToString));

        converterMap.put(RestfulResponse.getType(), xString.inst.getType(), (IDataProcessorBuilder::convertResponseToString));
        converterMap.put(WebSocketMessage.getType(), xString.inst.getType(), (IDataProcessorBuilder::convertResponseToString));
        converterMap.put(WebSocketMessage.getType(), TheStream.getType(), (IDataProcessorBuilder::convertResponseToStream));

        nativeConvertMap.put(xDecimal.inst.getType(), IDataProcessorBuilder::convertNativeToDecimal);
    }
    public static Variable convertType(Variable input, VariableType objectType, IDataProcessorBuilder builder) {

        if (input.getType() == objectType) {
            return input;
        }
        if (input.position == Variable.Position.STRING) {
            Func func = nativeConvertMap.get(objectType);
            if (func == null) {
                CEIErrors.showCodeFailure(TypeConverter.class,"Not support native converter, from: %s to %s", input.getType().getDescriptor(), objectType.getDescriptor());
                return null;
            }
            Variable result = func.convert(builder, input);
            if (result == null) {
                CEIErrors.showCodeFailure(TypeConverter.class,"Convert native result is null, from: %s to %s", input.getType().getDescriptor(), objectType.getDescriptor());
            }
            return result;
        }
        else {
            Func func = converterMap.get(input.getType(), objectType);
            if (func == null) {
                CEIErrors.showCodeFailure(TypeConverter.class,"Not support converter, from: %s to %s", input.getType().getDescriptor(), objectType.getDescriptor());
                return null;
            }
            Variable result = func.convert(builder, input);
            if (result == null) {
                CEIErrors.showCodeFailure(TypeConverter.class,"Convert result is null, from: %s to %s", input.getType().getDescriptor(), objectType.getDescriptor());
            }
            return result;
        }

    }
}
