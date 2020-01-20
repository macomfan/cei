package cn.ma.cei.xml;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.finalizer.TypeAlias;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class JsonToXml implements IXmlJsonConverter {
    private JSONObject jsonObject;

    public JsonToXml(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getType() {
        return jsonObject.getString("type");
    }


    @Override
    public void objectType(Object xmlObject) {
        if (!xmlObject.getClass().getName().equals(getType())) {
            throw new CEIException("[JsonToXml] Object type cannot match");
        }
    }

    @Override
    public void doAttribute(Object xmlObject, Field item) {
        try {
            item.set(xmlObject, jsonObject.get("_" + item.getName()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doObject(Object xmlObject, Field item) {
        JSONObject newJsonObject = jsonObject.getJSONObject("o_" + item.getName());
        if (newJsonObject == null) {
            // No data in json, ignore
            return;
        }
        JsonToXml jsonToXml = new JsonToXml(newJsonObject);
        Object obj;
        try {
            obj = item.get(xmlObject);
            if (obj == null) {
                obj = item.getType().newInstance();
            }
        } catch (Exception e) {
            throw new CEIException("[JsonToXml] Cannot create object");
        }

        Convert.doConvert(jsonToXml, obj);
        try {
            item.set(xmlObject, obj);
        } catch (Exception e) {
            throw new CEIException("[JsonToXml] Cannot set object");
        }
    }

    @Override
    public void doList(Object xmlObject, Field item) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("a_" + item.getName());
            if (jsonArray == null) {
                // No data in json. ignore.
                return;
            }
            Collection list = (Collection)item.get(xmlObject);

            if (list == null) {
                Collection newList = new ArrayList();
                item.set(xmlObject, newList);
                list = newList;
            }

            ParameterizedType listGenericType = (ParameterizedType) item.getGenericType();
            Type[] listActualTypeArguments = listGenericType.getActualTypeArguments();
            if (listActualTypeArguments.length == 0) {
                // error
            }
            Collection finalList = list;
            jsonArray.forEach((jsonObject) -> {
                try {
                    String typeName = ((JSONObject)jsonObject).getString("type");
                    Object newItemObject = Class.forName(TypeAlias.getClassNameByAlias(typeName)).newInstance();
                    JsonToXml jsonToXml = new JsonToXml((JSONObject)jsonObject);
                    Convert.doConvert(jsonToXml, newItemObject);
                    finalList.add(newItemObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
