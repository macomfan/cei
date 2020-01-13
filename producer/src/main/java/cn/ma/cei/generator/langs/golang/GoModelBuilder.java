/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang;

import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.golang.tools.GoFile;
import cn.ma.cei.generator.langs.golang.tools.GoStruct;
import cn.ma.cei.generator.langs.golang.tools.GoVar;

/**
 *
 * @author U0151316
 */
public class GoModelBuilder extends ModelBuilder {

    private GoStruct modelStruct = null;
    private GoFile mainFile = null;
    
    
    public GoModelBuilder(GoFile mainFile) {
        this.mainFile = mainFile;
    }
    
    @Override
    public String getRefrerence(String modelName) {
        return VariableFactory.NO_REF;
    }

    @Override
    public void startModel(VariableType modelType) {
        modelStruct = new GoStruct(modelType.getDescriptor());
    }

    @Override
    public void registerMember(Variable variable) {
        modelStruct.addMember(new GoVar(variable));
    }

    @Override
    public void endModel() {
        mainFile.addStruct(modelStruct);
    }
    
}
