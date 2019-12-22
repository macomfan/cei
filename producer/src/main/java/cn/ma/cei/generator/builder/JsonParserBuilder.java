package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;

public abstract class JsonParserBuilder {
    
    public abstract void getJsonString(Variable model, Variable to, Variable jsonObject, String itemName);

    public abstract void getJsonInteger(Variable model, Variable to, Variable jsonObject, String itemName);

    public abstract void getJsonObject(Variable jsonObject, Variable parentJsonObject, String itemName);

    public abstract void getJsonObjectArray(Variable jsonObject, Variable parentJsonObject, String itemName);

    public abstract void startJsonObjectArrayLoop(Variable eachItemJsonObject, Variable parentJsonObject);

    /***
     * parentModel.to.add(model)
     *
     * @param parentModel
     * @param to
     * @param model
     */
    public abstract void endJsonObjectArrayLoop(Variable parentModel, Variable to, Variable model);

    public abstract void defineModel(Variable model);

    public abstract void defineRootJsonObject(Variable jsonObject, Variable responseVariable);


}
