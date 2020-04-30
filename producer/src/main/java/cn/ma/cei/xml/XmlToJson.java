package cn.ma.cei.xml;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.finalizer.TypeAlias;
import io.vertx.core.json.JsonObject;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class XmlToJson implements IXmlJsonConverter {
    private final JsonObject jsonObject = new JsonObject();

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public void objectType(Object xmlObject) {
        jsonObject.put("type", TypeAlias.getAliasByClassName(xmlObject.getClass()));
    }

    @Override
    public void doAttribute(Object xmlObject, Field item) {
        try {
            item.setAccessible(true);
            if (item.get(xmlObject) != null) {
                jsonObject.put("_" + item.getName(), item.get(xmlObject));
            }
        } catch (Exception e) {
            throw new CEIException("[XmlToJson] Failed to do attribute: " + item.getName());
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
            List<JsonObject> res = new LinkedList<>();
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
        return jsonObject.toString();
    }
}
