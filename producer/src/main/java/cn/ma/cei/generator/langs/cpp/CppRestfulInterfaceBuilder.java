package cn.ma.cei.generator.langs.cpp;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.builder.ResponseBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.langs.cpp.tools.CppClass;
import cn.ma.cei.generator.langs.cpp.tools.CppMethod;

public class CppRestfulInterfaceBuilder extends RestfulInterfaceBuilder {
    
    private CppMethod cppMethod;
    private CppClass cppClass;
    
    public CppRestfulInterfaceBuilder(CppClass cppClass) {
        this.cppClass = cppClass;
    }
    
    @Override
    public ResponseBuilder getResponseBuilder() {
        return new CppResponseBuilder();
    }
    
    @Override
    public void startInterface(String restIfName) {
        cppMethod = new CppMethod(cppClass.getClassName());
        
    }
    
    @Override
    public void defineMethod(VariableType returnType, String methodDescriptor, VariableList params, MethodImplementation impl) {
        cppMethod.defineMethod(returnType, methodDescriptor, params, impl);
    }
    
    @Override
    public void setRequestTarget(Variable request, String target) {
        cppMethod.getCode().appendStatementWordsln(request.nameDescriptor + ".target", "=", cppMethod.getCode().toCppString(target));
    }
    
    @Override
    public void defineRequest(Variable request) {
        cppMethod.getCode().appendStatementWordsln(request.type.getDescriptor(), request.nameDescriptor);
    }
    
    @Override
    public void addToQueryStringByVariable(Variable request, String queryStringName, Variable variable) {
        cppMethod.getCode().appendStatementln(request.nameDescriptor + ".addQueryString(" + variable.nameDescriptor + ")");
    }
    
    @Override
    public void addToQueryStringByHardcode(Variable request, String queryStringName, String value) {
        cppMethod.getCode().appendStatementln(request.nameDescriptor + ".addQueryString(" + cppMethod.getCode().toCppString(value) + ")");
    }
    
    @Override
    public void invokeConnection(Variable request, Variable response) {
        
    }
    
    @Override
    public void setRequestMethod(Variable request, String requestMethodDescriptor) {
        
    }
    
    @Override
    public void returnResult(Variable returnVariable) {
        
    }
    
    @Override
    public void endInterface() {
        cppClass.addMethod(cppMethod);
    }
    
    @Override
    public void onAddReference(VariableType variableType) {
        
    }
}
