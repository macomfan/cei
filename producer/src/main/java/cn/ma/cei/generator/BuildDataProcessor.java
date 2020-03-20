package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.dataprocessor.*;
import cn.ma.cei.model.authentication.xAddQueryString;
import cn.ma.cei.model.authentication.xCombineQueryString;
import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.model.json.xJsonParser;
import cn.ma.cei.model.processor.xBase64;
import cn.ma.cei.model.processor.xGetNow;
import cn.ma.cei.model.processor.xHmacSHA256;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.NormalMap;


import java.util.List;

public class BuildDataProcessor {

    private static NormalMap<Class<?>, DataProcessorBase<?>> processorMap = new NormalMap<>();

    static {
        processorMap.put(xGetNow.class, new GetNow());
        processorMap.put(xBase64.class, new Base64());
        processorMap.put(xHmacSHA256.class, new HmacSHA256());
        processorMap.put(xAddQueryString.class, new AddQueryString());
        processorMap.put(xCombineQueryString.class, new CombineQueryString());
        processorMap.put(xJsonParser.class, new JsonParser());
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
                // TODO
                CEIErrors.showFailure(CEIErrorType.CODE, "not supported");
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
                CEIErrors.showFailure(CEIErrorType.CODE, "not supported");
            }
        }
        return null;
    }

    public static Variable build(List<xDataProcessorItem> items, Variable defaultInput, String resultVariableName, IDataProcessorBuilder builder) {
        if (items.size() == 1 && Checker.isEmpty(resultVariableName)) {
            // Only one item in the processor list, return the only item. Do not define output name in this item.
            if (processorMap.containsKey(items.get(0).getClass())) {
                return processorMap.get(items.get(0).getClass()).callBuild(items.get(0), defaultInput, builder);
            } else {
                // TODO
                CEIErrors.showFailure(CEIErrorType.CODE, "not supported");
            }
        }

        items.forEach(item -> {
            if (processorMap.containsKey(item.getClass())) {
                Variable result = processorMap.get(item.getClass()).callBuild(item, defaultInput, builder);
            } else {
                // TODO
                CEIErrors.showFailure(CEIErrorType.CODE, "not supported");
            }
        });
        return null;
    }
}
