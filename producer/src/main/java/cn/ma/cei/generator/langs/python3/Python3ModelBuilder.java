/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.python3.tools.Python3Class;
import cn.ma.cei.generator.langs.python3.tools.Python3File;

/**
 *
 * @author U0151316
 */
public class Python3ModelBuilder extends ModelBuilder {

    private Python3File mainFile;
    private Python3Class modelClass;
    
    public Python3ModelBuilder(Python3File mainFile) {
        this.mainFile = mainFile;
    }
    
    @Override
    public String getReference(String modelName) {
        return VariableFactory.NO_REF;
    }

    @Override
    public void startModel(VariableType modelType) {
        modelClass = new Python3Class(modelType.getDescriptor());
    }

    @Override
    public void registerMember(Variable variable) {
        modelClass.addMemberVariable(variable);
    }

    @Override
    public void endModel() {
        mainFile.addInnerClass(modelClass);
    }
    
}
