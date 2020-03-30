package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.generator.buildin.StringWrapper;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;

public class TypeConverter {
    public static Variable convertType(Variable input, VariableType objectType, IDataProcessorBuilder builder) {
        if (input.getType() == objectType) {
            return input;
        } else if (input.getType() == JsonWrapper.getType()) {
            if (objectType == xString.inst.getType()) {
                return builder.jsonWrapperToString(input);
            } else {
                CEIErrors.showFailure(CEIErrorType.XML, "Cannot convert JsonWrapper to %s", objectType.getDescriptor());
            }
        } else if (input.getType() == StringWrapper.getType()) {
            if (objectType == xString.inst.getType()) {
                return builder.stringWrapperToString(input);
            } else {
                CEIErrors.showFailure(CEIErrorType.XML, "Cannot convert StringWrapper to %s", objectType.getDescriptor());
            }
        } else if (input.getType() == xString.inst.getType()){
            //if (objectType == xS)


        } else if (input.getType() == xInt.inst.getType()) {
            if (objectType == xString.inst.getType()) {
                return builder.convertIntToString(input);
            }
        }

        else{
            CEIErrors.showFailure(CEIErrorType.XML, "Not support converter, from: %s, to %s", input.getType().getName(), objectType.getName());
        }
        return null;
    }
}
