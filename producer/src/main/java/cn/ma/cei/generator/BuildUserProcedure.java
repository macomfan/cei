package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IMethodBuilder;
import cn.ma.cei.generator.dataprocessor.TypeConverter;
import cn.ma.cei.model.base.xItemWithProcedure;
import cn.ma.cei.model.xProcedure;
import cn.ma.cei.utils.Checker;

public class BuildUserProcedure {

//    public static Variable buildUserProcedure(VariableType objType, String resultValue, xProcedure procedureItem, IMethodBuilder methodBuilder) {
//
//    }

    public static Variable createValueFromProcedure(VariableType objType, String value, xProcedure parent, IMethodBuilder methodBuilder) {
        Checker.isNull(methodBuilder, BuildUserProcedure.class, "IMethodBuilder");
        Checker.isNull(parent, BuildUserProcedure.class, "xItemWithProcedure");
        IDataProcessorBuilder dataProcessorBuilder = Checker.checkNull(methodBuilder.createDataProcessorBuilder(), methodBuilder, "DataProcessorBuilder");
        Checker.isNull(dataProcessorBuilder, BuildUserProcedure.class, "IDataProcessorBuilder");
        Variable result = innerCreateValueFromProcedure(value, parent, dataProcessorBuilder);
        return TypeConverter.convertType(result, objType, dataProcessorBuilder);
    }

    public static Variable createValueFromProcedure(String value, xProcedure parent, IMethodBuilder methodBuilder) {
        Checker.isNull(methodBuilder, BuildUserProcedure.class, "IMethodBuilder");
        Checker.isNull(parent, BuildUserProcedure.class, "xItemWithProcedure");
        IDataProcessorBuilder dataProcessorBuilder = methodBuilder.createDataProcessorBuilder();
        Checker.isNull(dataProcessorBuilder, BuildUserProcedure.class, "IDataProcessorBuilder");
        return innerCreateValueFromProcedure(value, parent, dataProcessorBuilder);
    }

    public static Variable innerCreateValueFromProcedure(String value, xProcedure parent, IDataProcessorBuilder dataProcessorBuilder) {
        if (Checker.isEmpty(value)) {
            CEIErrors.showCodeFailure(BuildUserProcedure.class, "Value is null.");
        }

        if (parent.items != null) {
            // Build processor firstly.
            BuildDataProcessor.build(parent, null, value, dataProcessorBuilder);
        }
        return GlobalContext.getCurrentMethod().queryVariableOrConstant(value, dataProcessorBuilder);
    }

//    private static Variable createUserDefinedValue(String value, IDataProcessorBuilder builder) {
//        if (Checker.isEmpty(value)) {
//            CEIErrors.showCodeFailure(BuildUserProcedure.class, "Value is null.");
//        }
//        if (RegexHelper.isReference(value) != null) {
//            // {xxx}
//            Variable var = GlobalContext.getCurrentMethod().getVariable(RegexHelper.isReference(value));
//            if (var == null) {
//                throw new CEIException("Cannot lookup variable: " + var);
//            }
//            return var;
//        } else {
//            List<String> linkedParam = RegexHelper.findReference(value);
//            if (linkedParam.isEmpty()) {
//                return GlobalContext.createStringConstant(value);
//            } else {
//                List<Variable> variables = new LinkedList<>();
//                variables.add(GlobalContext.createStringConstant(value));
//                linkedParam.forEach(item -> {
//                    Variable param = GlobalContext.getCurrentMethod().getVariable(item);
//                    if (param == null) {
//                        throw new CEIException("Cannot find variable in target");
//                    }
//                    variables.add(TypeConverter.convertType(param, xString.inst.getType(), builder));
//                });
//                Variable[] params = new Variable[variables.size()];
//                variables.toArray(params);
//                return builder.stringReplacement(params);
//            }
//        }
//    }

//        public static Variable createValueFromAttribute (String attrName, xAttributeExtension various, IMethodBuilder
//        methodBuilder){
//            Checker.isNull(methodBuilder, BuildUserProcedure.class, "IMethodBuilder");
//            IDataProcessorBuilder dataProcessorBuilder = methodBuilder.createDataProcessorBuilder();
//            Checker.isNull(dataProcessorBuilder, BuildUserProcedure.class, "IDataProcessorBuilder");
//            Field field = ReflectionHelper.getFieldByName(various, attrName);
//            if (field == null) {
//                throw new CEIException("Cannot find attribute " + attrName);
//            }
//            String attrValue = null;
//            try {
//                attrValue = (String) field.get(various);
//            } catch (IllegalArgumentException | IllegalAccessException ignored) {
//
//            }
//            if (attrValue == null) {
//                // not define the attribute, should read <attribute> session
//                xAttribute valueProcessor = various.getAttributeByName(attrName);
//                if (valueProcessor == null) {
//                    throw new CEIException("Attribute " + attrName + " do not have both value and value extension");
//                }
//                return BuildDataProcessor.build(valueProcessor.items, null, valueProcessor.result, dataProcessorBuilder);
//            } else if (RegexHelper.isReference(attrValue) != null) {
//                Variable var = GlobalContext.getCurrentMethod().getVariable(RegexHelper.isReference(attrValue));
//                if (var == null) {
//                    throw new CEIException("Cannot lookup variable: " + var);
//                }
//                return var;
//            } else {
//                List<String> linkedParam = RegexHelper.findReference(attrValue);
//                if (linkedParam.isEmpty()) {
//                    return GlobalContext.createStringConstant(attrValue);
//                } else {
//                    List<Variable> variables = new LinkedList<>();
//                    variables.add(GlobalContext.createStringConstant(attrValue));
//                    linkedParam.forEach(item -> {
//                        Variable param = GlobalContext.getCurrentMethod().getVariable(item);
//                        if (param == null) {
//                            throw new CEIException("Cannot find variable in target");
//                        }
//                        variables.add(param);
//                    });
//                    Variable[] params = new Variable[variables.size()];
//                    variables.toArray(params);
//                    return dataProcessorBuilder.stringReplacement(params);
//                }
//            }
//        }
}
