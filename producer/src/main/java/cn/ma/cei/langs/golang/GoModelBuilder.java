/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IModelBuilder;
import cn.ma.cei.langs.golang.tools.GoFile;
import cn.ma.cei.langs.golang.tools.GoStruct;
import cn.ma.cei.langs.golang.vars.GoVar;

/**
 *
 * @author U0151316
 */
public class GoModelBuilder implements IModelBuilder {

    private GoStruct modelStruct = null;
    private final GoFile mainFile;
    
    
    public GoModelBuilder(GoFile mainFile) {
        this.mainFile = mainFile;
    }
    
    @Override
    public String getReference(String modelName) {
        return BuilderContext.NO_REF;
    }

    @Override
    public void startModel(VariableType modelType) {
        modelStruct = new GoStruct(modelType.getDescriptor());
    }

    @Override
    public void addMember(Variable variable) {
        modelStruct.addPublicMember(modelStruct.var(variable));
    }

    @Override
    public void endModel() {
        mainFile.addStruct(modelStruct);
    }
    
}
