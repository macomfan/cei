/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.python3;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IModelBuilder;
import cn.ma.cei.langs.python3.tools.Python3Class;
import cn.ma.cei.langs.python3.tools.Python3File;

/**
 *
 * @author U0151316
 */
public class Python3ModelBuilder implements IModelBuilder {

    private final Python3File mainFile;
    private Python3Class modelClass;
    
    public Python3ModelBuilder(Python3File mainFile) {
        this.mainFile = mainFile;
    }
    
    @Override
    public String getReference(String modelName) {
        return BuilderContext.NO_REF;
    }

    @Override
    public void startModel(VariableType modelType) {
        modelClass = new Python3Class(modelType.getDescriptor());
    }

    @Override
    public void addMember(Variable variable) {
        modelClass.addMemberVariable(variable);
    }

    @Override
    public void endModel() {
        mainFile.addInnerClass(modelClass);
    }
    
}
