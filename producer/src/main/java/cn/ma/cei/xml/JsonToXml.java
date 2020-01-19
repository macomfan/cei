package cn.ma.cei.xml;

import cn.ma.cei.exception.CEIException;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

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
        if (!xmlObject.getClass().getSimpleName().equals(getType())) {
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
        Object obj = null;
        try {
            obj = item.get(xmlObject);
            if (obj == null) {
                obj = item.getType().newInstance();
            }
        } catch (Exception e) {
            throw new CEIException("[JsonToXml] Cannot create object");
        }
        JsonToXml jsonToXml = new JsonToXml(jsonObject.getJSONObject("o_" + item.getName()));
        Convert.doConvert(jsonToXml, obj);
    }

    @Override
    public void doList(Object xmlObject, Field item) {
        try {
            Object obj = item.get(xmlObject);
            //List<?> list = (List<?>);
            ParameterizedType listGenericType = (ParameterizedType) item.getGenericType();
            Type[] listActualTypeArguments = listGenericType.getActualTypeArguments();
            System.out.println(listActualTypeArguments[listActualTypeArguments.length-1]);
            for (int i = 0; i < listActualTypeArguments.length; i++) {
                System.out.println(listActualTypeArguments[i]);
            }

            Class<? extends LinkedList> aa = String.class.asSubclass(LinkedList.class);
            System.out.println(listActualTypeArguments[listActualTypeArguments.length-1]);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
