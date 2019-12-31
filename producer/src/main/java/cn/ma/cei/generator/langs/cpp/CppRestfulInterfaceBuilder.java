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
    
//    @Override
//    public void setRequestTarget(Variable request, String target) {
//        cppMethod.getCode().appendStatementWordsln(request.nameDescriptor + ".target", "=", cppMethod.getCode().toCppString(target));
//    }
    
    @Override
    public void defineRequest(Variable request) {
        cppMethod.getCode().appendStatementWordsln(request.type.getDescriptor(), request.nameDescriptor);
    }
    
    
    @Override
    public void invokeQuery(Variable request, Variable response) {
        
    }
    
    @Override
    public void returnResult(Variable returnVariable) {
        
    }
    
    @Override
    public void onAddReference(VariableType variableType) {
        
    }


    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, VariableList params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void endMethod() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addHeader(Variable request, Variable tag, Variable value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addToQueryString(Variable request, Variable queryStringName, Variable variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setRequestTarget(Variable request, Variable target) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUrl(Variable request, Variable url) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setRequestMethod(Variable request, Variable requestMethod) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
