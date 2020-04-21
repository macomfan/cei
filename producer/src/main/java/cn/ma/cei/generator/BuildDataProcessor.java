package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.dataprocessor.*;
import cn.ma.cei.generator.dataprocessor.BuildJsonParser;
import cn.ma.cei.model.authentication.xAddQueryString;
import cn.ma.cei.model.authentication.xCombineQueryString;
import cn.ma.cei.model.authentication.xGetRequestInfo;
import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.model.json.xJsonBuilder;
import cn.ma.cei.model.json.xJsonParser;
import cn.ma.cei.model.processor.*;
import cn.ma.cei.model.string.xStringBuilder;
import cn.ma.cei.model.xProcedure;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.NormalMap;
import cn.ma.cei.utils.ReflectionHelper;
import cn.ma.cei.utils.RegexHelper;


import java.util.List;

/**
 * To build each line in User Procedure.
 */
public class BuildDataProcessor {

    private static NormalMap<Class<?>, DataProcessorBase<?>> processorMap = new NormalMap<>();

    static {
        processorMap.put(xGetNow.class, new BuildGetNow());
        processorMap.put(xBase64.class, new BuildBase64());
        processorMap.put(xHmacSHA256.class, new BuildHmacSHA256());
        processorMap.put(xGetRequestInfo.class, new BuildGetRequestInfo());
        processorMap.put(xAddQueryString.class, new BuildAddQueryString());
        processorMap.put(xCombineQueryString.class, new BuildCombineQueryString());
        processorMap.put(xJsonParser.class, new BuildJsonParser());
        processorMap.put(xJsonBuilder.class, new BuildJsonBuilder());
        processorMap.put(xStringBuilder.class, new BuildStringWrapper());
        processorMap.put(xURLEscape.class, new BuildURLEscape());
        processorMap.put(xGZip.class, new BuildGZip());
    }


    public static VariableType getResultType(List<xDataProcessorItem> items, String resultVariableName) {
        if (items == null) {
            return null;
        }

        if (items.size() == 1 && Checker.isEmpty(resultVariableName)) {
            // Only one item in the processor list, return the only item. Do not define output name in this item.
            if (processorMap.containsKey(items.get(0).getClass())) {
                return processorMap.get(items.get(0).getClass()).callReturnType(items.get(0));
            } else {
                CEIErrors.showFailure(CEIErrorType.CODE, "Processor is not supporting %s", items.get(0).getClass().getName());
            }
        }

        for (xDataProcessorItem item : items) {
            if (processorMap.containsKey(item.getClass())) {
                DataProcessorBase<?> processor = processorMap.get(item.getClass());
                String resultInProcessor = processor.callResultVariableName(item);
                if (resultInProcessor.equals(resultVariableName)) {
                    return processor.callReturnType(item);
                }
            } else {
                // TODO
                CEIErrors.showFailure(CEIErrorType.CODE, "Processor is not supporting %s", items.get(0).getClass().getName());
            }
        }
        return null;
    }

    public static void build(List<xDataProcessorItem> items, IDataProcessorBuilder builder) {
        items.forEach(item -> {
            if (item instanceof xProcedure) {
                build(((xProcedure)item).items, builder);
            } else {
                processSingleItem(item, null, builder);
            }
        });
    }

    public static Variable build(List<xDataProcessorItem> items, Variable defaultInput, String resultVariableName, IDataProcessorBuilder builder) {
        if (Checker.isEmpty(resultVariableName)){
            if (items.size() == 1) {
                // Only one item in the processor list, return the only item. Do not define output name in this item.
                return processSingleItem(items.get(0), defaultInput, builder);
            } else {
                CEIErrors.showFailure(CEIErrorType.XML, "Must define result, there are multi-processor items.");
                return null;
            }
        }
        items.forEach(item -> {
            processSingleItem(item, defaultInput, builder);
        });

        Variable result = GlobalContext.getCurrentMethod().queryVariable(resultVariableName);
        if (result == null) {
            CEIErrors.showFailure(CEIErrorType.XML, "Cannot find the result variable: %s", RegexHelper.isReference(resultVariableName));
            return null;
        }
        return result;
    }

    private static Variable processSingleItem(xDataProcessorItem item, Variable defaultInput, IDataProcessorBuilder builder) {
        if (processorMap.containsKey(item.getClass())) {
            return processorMap.get(item.getClass()).callBuild(item, defaultInput, builder);
        } else {
            // TODO
            CEIErrors.showFailure(CEIErrorType.CODE, "not supported");
            return null;
        }
    }
}
