package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;

public interface IJsonParserBuilder extends IBuilderBase {

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
    
    void assignJsonStringArray(Variable to, Variable jsonObject, Variable itemName);

    void assignJsonDecimalArray(Variable to, Variable jsonObject, Variable itemName);

    void assignJsonBooleanArray(Variable to, Variable jsonObject, Variable itemName);

    void assignJsonIntArray(Variable to, Variable jsonObject, Variable itemName);

    void getJsonArray(Variable jsonWrapperObject, Variable jsonObject, Variable itemName);

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

    IJsonCheckerBuilder createJsonCheckerBuilder();
}
