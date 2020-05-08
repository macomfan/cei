package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IMethodBuilder;
import cn.ma.cei.generator.buildin.TheJson;
import cn.ma.cei.generator.buildin.TheStream;
import cn.ma.cei.generator.dataprocessor.TypeConverter;
import cn.ma.cei.model.json.xJsonParser;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.xResponse;
import cn.ma.cei.utils.Checker;

public class BuildResponse {

    public static VariableType getReturnType(xResponse response) {
        if (!Checker.isEmpty(response.type)) {
            if (!Checker.isNull(response.items)) {
                CEIErrors.showXMLFailure("Cannot define both type and procedure.");
            }
            if ("string".equals(response.type)) {
                return xString.inst.getType();
            } else if ("binary".equals(response.type)) {
                return TheStream.getType();
            } else if ("json".equals(response.type)) {
                // TODO to be supported
                return null;
            } else {
                CEIErrors.showXMLFailure("Type is invalid.");
                return null;
            }
        } else {
            VariableType returnType = BuildDataProcessor.getReturnType(response, response.result);
            if (returnType == null) {
                if (Checker.isEmpty(response.result)) {
                    if (!Checker.isNull(response.items)) {
                        if (response.items.size() == 1) {
                            CEIErrors.showXMLFailure("Cannot decide the return type automatically.");
                        } else {
                            CEIErrors.showXMLFailure("Cannot decide the return type automatically, there are multi items in response");
                        }
                    }
                } else {
                    CEIErrors.showXMLFailure("Cannot find the return variable %s", response.result);
                }
            }
            return returnType;
        }
    }

    public static Variable build(xResponse response, Variable responseVariable, IMethodBuilder methodBuilder) {
        IDataProcessorBuilder dataProcessorBuilder =
                Checker.checkNull(methodBuilder.createDataProcessorBuilder(), methodBuilder, "DataProcessorBuilder");
        if ("string".equals(response.type)) {
            return TypeConverter.convertType(responseVariable, xString.inst.getType(), dataProcessorBuilder);
        } else if ("binary".equals(response.type)) {
            return TypeConverter.convertType(responseVariable, TheStream.getType(), dataProcessorBuilder);
        } else if ("json".equals(response.type)) {
            // TODO to be support
            return TypeConverter.convertType(responseVariable, TheJson.getType(), dataProcessorBuilder);
        }else {
            if (Checker.isNull(response.items)) {
                return null;
            }
            response.items.forEach(item -> {
                if (item instanceof xJsonParser) {
                    if (((xJsonParser) item).jsonChecker != null)
                        ((xJsonParser) item).jsonChecker.usedFor = IJsonCheckerBuilder.UsedFor.REPORT_ERROR;
                }
            });
            return BuildDataProcessor.build(response, responseVariable, response.result, dataProcessorBuilder);
        }
    }
}
