package cn.ma.cei.xml;

import cn.ma.cei.exception.CEIException;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class XmlToJson implements IXmlJsonConverter {
    private JSONObject jsonObject = new JSONObject();


    @Override
    public void objectType(Object xmlObject) {
        jsonObject.put("type", xmlObject.getClass().getSimpleName());
    }

    @Override
    public void doAttribute(Object xmlObject, Field item) {
        try {
            jsonObject.put("_" + item.getName(), item.get(xmlObject));
        } catch (Exception e) {
            throw new CEIException("[XmlToJson] Failed to do attribute");
        }
    }

    @Override
    public void doObject(Object xmlObject, Field item) {
        XmlToJson xmlToJson = new XmlToJson();
        try {
            Object obj = item.get(xmlObject);
            if (obj == null) {
                return;
            }
            Convert.doConvert(xmlToJson, obj);
            jsonObject.put("o_" + item.getName(), xmlToJson.jsonObject);
        } catch (Exception e) {
            throw new CEIException("[XmlToJson] Failed to do object");
        }
    }

    @Override
    public void doList(Object xmlObject, Field item) {
        try {
            List<JSONObject> res = new LinkedList<>();
            List<?> list = (List<?>) item.get(xmlObject);
            if (list == null) {
                return;
            }
            list.forEach(itemInList -> {
                XmlToJson xmlToJson = new XmlToJson();
                Convert.doConvert(xmlToJson, itemInList);
                res.add(xmlToJson.jsonObject);
            });
            jsonObject.put("a_" + item.getName(), res);
        } catch (Exception e) {
            throw new CEIException("[XmlToJson] Failed to do list: " + xmlObject.getClass().getSimpleName() + " " + item.getName());
        }
    }

    public String toJsonString() {
        return jsonObject.toJSONString();
    }
}
