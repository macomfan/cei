package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.buildin.TheStream;
import cn.ma.cei.generator.dataprocessor.Base64;
import cn.ma.cei.generator.dataprocessor.GetNow;
import cn.ma.cei.generator.dataprocessor.HmacSHA256;
import cn.ma.cei.generator.dataprocessor.JsonParser;
import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.model.json.xJsonBuilder;
import cn.ma.cei.model.json.xJsonParser;
import cn.ma.cei.model.processor.xBase64;
import cn.ma.cei.model.processor.xGetNow;
import cn.ma.cei.model.processor.xHmacSHA256;
import cn.ma.cei.model.string.xStringBuilder;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.NormalMap;
import cn.ma.cei.utils.RegexHelper;
import com.sun.org.apache.xpath.internal.operations.String;

import java.util.List;

public class BuildDataProcessor {

    private static NormalMap<Class<?>, DataProcessorBase<?>> processorMap = new NormalMap<>();

    static {
        processorMap.put(xGetNow.class, new GetNow());
        processorMap.put(xBase64.class, new Base64());
        processorMap.put(xHmacSHA256.class, new HmacSHA256());
        processorMap.put(xJsonParser.class, new JsonParser());
    }


    @FunctionalInterface
    public interface SlotFunction {
        void func(xDataProcessorItem item);
    }

    public static VariableType getResultType(List<xDataProcessorItem> items, String resultVariableName, SlotFunction slot) {
        if (items == null) {
            return null;
        }

        items.forEach(item -> {
            if (item instanceof xGetNow) {
                //checkReturnForGetNow()
            }
        });
        return null;
    }

    private static VariableType checkReturnForGetNow(xGetNow getNow, String resultVariableName) {
        if (resultVariableName.equals(getNow.name)) {
            return xString.inst.getType();
        }
        return null;
    }

    public static Variable build(List<xDataProcessorItem> items, Variable defaultInput, IDataProcessorBuilder builder, SlotFunction slot) {

        sMethod method = GlobalContext.getCurrentMethod();

        items.forEach(item -> {
            if (processorMap.containsKey(item.getClass())) {
                processorMap.get(item.getClass()).startBuild(item, defaultInput, builder);
            } else {
                slot.func(item);
            }
        });
        return null;
    }

    public static void processGetNow(xGetNow getNow, IDataProcessorBuilder builder) {

    }

//    public static void processBase64(xBase64 base64, IDataProcessorBuilder builder) {
//        if (Checker.isEmpty(base64.output)) {
//            throw new CEIException("[BuildSignature] output must be defined for base64");
//        }
//        if (Checker.isEmpty(base64.input)) {
//            throw new CEIException("[BuildSignature] input must be defined for base64");
//        }
//        Variable input = queryVariable(base64.input);
//        Variable output = GlobalContext.getCurrentMethod().createLocalVariable(xString.inst.getType(), RegexHelper.isReference(base64.output));
//        builder.base64(output, input);
//    }
//
//    public static void processHmacsha256(xHmacSHA256 hmacsha256, IDataProcessorBuilder builder) {
//        if (Checker.isEmpty(hmacsha256.output)) {
//            throw new CEIException("[BuildSignature] output must be defined for hmacsha256");
//        }
//        if (Checker.isEmpty(hmacsha256.input)) {
//            throw new CEIException("[BuildSignature] input must be defined for hmacsha256");
//        }
//        if (Checker.isEmpty(hmacsha256.key)) {
//            throw new CEIException("[BuildSignature] key must be defined for hmacsha256");
//        }
//        Variable input = queryVariable(hmacsha256.input);
//        Variable key = queryVariable(hmacsha256.key);
//        Variable output = GlobalContext.getCurrentMethod().createLocalVariable(TheStream.getType(), RegexHelper.isReference(hmacsha256.output));
//        builder.hmacsha265(output, input, key);
//    }
//
//    private static Variable queryVariable(String name) {
//        Variable variable = GlobalContext.getCurrentMethod().getVariableAsParam(name);
//        if (variable == null) {
//            Variable options = GlobalContext.getCurrentMethod().getVariable("options");
//            if (options == null) {
//                throw new CEIException("[BuildSignature] Cannot query option");
//            }
//            String variableName = RegexHelper.isReference(name);
//            variable = options.getMember(variableName);
//            if (variable == null) {
//                throw new CEIException("[BuildSignature] cannot query variable: " + name);
//            }
//        }
//        return variable;
//    }
}
