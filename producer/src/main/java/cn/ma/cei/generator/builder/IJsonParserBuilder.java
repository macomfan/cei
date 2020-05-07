package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;

public interface IJsonParserBuilder extends IBuilderBase {

    /***
     * Should be [ value.nameDescriptor = jsonObject.nameDescriptor.getString(key) ]
     *  @param value
     * @param jsonObject
     * @param key
     * @param optional
     */    
    void getJsonString(Variable value, Variable jsonObject, Variable key, boolean optional);

    void getJsonInteger(Variable value, Variable jsonObject, Variable key, boolean optional);

    void getJsonBoolean(Variable value, Variable jsonObject, Variable key, boolean optional);
    
    void getJsonDecimal(Variable value, Variable jsonObject, Variable key, boolean optional);
    
    void assignJsonStringArray(Variable value, Variable jsonObject, Variable key, boolean optional);

    void assignJsonDecimalArray(Variable value, Variable jsonObject, Variable key, boolean optional);

    void assignJsonBooleanArray(Variable value, Variable jsonObject, Variable key, boolean optional);

    void assignJsonIntArray(Variable value, Variable jsonObject, Variable key, boolean optional);

    /**
     * parentJsonObject = parentJsonObject.getArray( key )
     *
     * @param jsonObject
     * @param parentJsonObject
     * @param key
     * @param optional
     */
    void getJsonArray(Variable jsonObject, Variable parentJsonObject, Variable key, boolean optional);

    /***
     * jsonObject = parentJsonObject.getObject( key )
     *
     * @param jsonObject
     * @param parentJsonObject
     * @param key
     */
    void getJsonObject(Variable jsonObject, Variable parentJsonObject, Variable key, boolean optional);

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
     * parentModel.value.add(model)
     *
     * @param value
     * @param model
     */
    void endJsonObjectArray(Variable value, Variable model);

    void defineModel(Variable model);

    void defineRootJsonObject(Variable jsonObject, Variable stringVariable);

    IJsonCheckerBuilder createJsonCheckerBuilder();
}
