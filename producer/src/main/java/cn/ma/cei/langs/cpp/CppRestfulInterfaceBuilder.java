package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.langs.cpp.tools.CppClass;
import cn.ma.cei.langs.cpp.tools.CppMethod;
import java.util.List;

public class CppRestfulInterfaceBuilder implements IRestfulInterfaceBuilder {
    
    private CppMethod cppMethod;
    private CppClass cppClass;
    
    public CppRestfulInterfaceBuilder(CppClass cppClass) {
        this.cppClass = cppClass;
    }

//    @Override
//    public void setRequestTarget(Variable request, String target) {
//        cppMethod.getCode().appendStatementWordsln(request.nameDescriptor + ".target", "=", cppMethod.getCode().toCppString(target));
//    }
    
    @Override
    public void defineRequest(Variable request, Variable option) {
        cppMethod.getCode().appendStatementWordsln(request.getTypeDescriptor(), request.getDescriptor());
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

//    @Override
//    public IStringBuilderBuilder createStringBuilderBuilder() {
//        return null;
//    }
//
//    @Override
//    public IJsonParserBuilder createJsonParserBuilder() {
//        return new CppJsonParserBuilder();
//    }


    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return null;
    }

    @Override
    public void endMethod(Variable returnVariable) {
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

//    @Override
//    public void setUrl(Variable request) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public void setRequestMethod(Variable request, Variable requestMethod) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPostBody(Variable request, Variable postBody) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public IJsonBuilderBuilder createJsonBuilderBuilder() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public void invokeAuthentication(Variable request, String methodName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
