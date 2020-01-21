package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.environment.Variable;

public abstract class JsonParserBuilder {

    /***
     * Should be [ to.nameDescriptor = jsonObject.nameDescriptor.getString(itemName) ]
     * 
     * @param to
     * @param jsonObject
     * @param itemName 
     */    
    public abstract void getJsonString(Variable to, Variable jsonObject, Variable itemName);

    public abstract void getJsonInteger(Variable to, Variable jsonObject, Variable itemName);

    public abstract void getJsonBoolean(Variable to, Variable jsonObject, Variable itemName);
    
    public abstract void getJsonDecimal(Variable to, Variable jsonObject, Variable itemName);
    
    public abstract void getJsonStringArray(Variable to, Variable jsonObject, Variable itemName);

    /***
     * jsonObject = parentJsonObject.getObject( itemName )
     *
     * @param jsonObject
     * @param parentJsonObject
     * @param itemName 
     */
    public abstract void startJsonObject(Variable jsonObject, Variable parentJsonObject, Variable itemName);

    /***
     * to = parentModel
     *
     * @param to
     * @param model
     */
    public abstract void endJsonObject(Variable to, Variable model);

    /**
     * for eachItemJsonObject in parentJsonObject.getObjectArray(itemName)
     *
     * @param eachItemJsonObject
     * @param parentJsonObject
     * @param itemName
     */
    public abstract void startJsonObjectArray(Variable eachItemJsonObject, Variable parentJsonObject, Variable itemName);

    /***
     * parentModel.to.add(model)
     *
     * @param to
     * @param model
     */
    public abstract void endJsonObjectArray(Variable to, Variable model);

    /**
     * for eachItemJsonObject in parentJsonObject.getArray(itemName)
     * @param eachItemJsonObject
     * @param parentJsonObject
     * @param itemName
     */
    public abstract void startArray(Variable eachItemJsonObject, Variable parentJsonObject, Variable itemName);

    /**
     * end for
     * @param to
     * @param model
     */
    public abstract void endJsonArray(Variable to, Variable model);

    public abstract void defineModel(Variable model);

    public abstract void defineRootJsonObject(Variable jsonObject, Variable responseVariable);


}
