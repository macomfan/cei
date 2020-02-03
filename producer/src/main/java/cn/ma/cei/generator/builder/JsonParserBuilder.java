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

    public abstract void getJsonDecimalArray(Variable to, Variable jsonObject, Variable itemName);

    public abstract void getJsonBooleanArray(Variable to, Variable jsonObject, Variable itemName);

    public abstract void getJsonIntArray(Variable to, Variable jsonObject, Variable itemName);

    /***
     * jsonObject = parentJsonObject.getObject( itemName )
     *
     * @param jsonObject
     * @param parentJsonObject
     * @param itemName 
     */
    public abstract void defineJsonObject(Variable jsonObject, Variable parentJsonObject, Variable itemName);

    /***
     * value = parentModel
     *
     * @param value
     * @param model
     */
    public abstract void assignModel(Variable value, Variable model);

    /**
     * for eachItemJsonObject in jsonObject.forEach
     *
     * @param eachItemJsonObject
     * @param jsonObject
     */
    public abstract void startJsonObjectArray(Variable eachItemJsonObject, Variable jsonObject);

    /***
     * parentModel.to.add(model)
     *
     * @param to
     * @param model
     */
    public abstract void endJsonObjectArray(Variable to, Variable model);

    public abstract void defineModel(Variable model);

    public abstract void defineRootJsonObject(Variable jsonObject, Variable responseVariable);

    public abstract JsonCheckerBuilder createJsonCheckerBuilder();
}
