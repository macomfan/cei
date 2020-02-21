package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;

public interface IJsonParserBuilder {

    /***
     * Should be [ to.nameDescriptor = jsonObject.nameDescriptor.getString(itemName) ]
     * 
     * @param to
     * @param jsonObject
     * @param itemName 
     */    
    void getJsonString(Variable to, Variable jsonObject, Variable itemName);

    void getJsonInteger(Variable to, Variable jsonObject, Variable itemName);

    void getJsonBoolean(Variable to, Variable jsonObject, Variable itemName);
    
    void getJsonDecimal(Variable to, Variable jsonObject, Variable itemName);
    
    void getJsonStringArray(Variable to, Variable jsonObject, Variable itemName);

    void getJsonDecimalArray(Variable to, Variable jsonObject, Variable itemName);

    void getJsonBooleanArray(Variable to, Variable jsonObject, Variable itemName);

    void getJsonIntArray(Variable to, Variable jsonObject, Variable itemName);

    /***
     * jsonObject = parentJsonObject.getObject( itemName )
     *
     * @param jsonObject
     * @param parentJsonObject
     * @param itemName 
     */
    void defineJsonObject(Variable jsonObject, Variable parentJsonObject, Variable itemName);

    /***
     * value = parentModel
     *
     * @param value
     * @param model
     */
    void assignModel(Variable value, Variable model);

    /**
     * for eachItemJsonObject in jsonObject.forEach
     *
     * @param eachItemJsonObject
     * @param jsonObject
     */
    void startJsonObjectArray(Variable eachItemJsonObject, Variable jsonObject);

    /***
     * parentModel.to.add(model)
     *
     * @param to
     * @param model
     */
    void endJsonObjectArray(Variable to, Variable model);

    void defineModel(Variable model);

    void defineRootJsonObject(Variable jsonObject, Variable responseVariable);

    JsonCheckerBuilder createJsonCheckerBuilder();
}
