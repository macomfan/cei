/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.exception;

import com.alibaba.fastjson.JSONObject;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import javafx.util.Pair;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author u0151316
 */
public class BuildTracer {

    private static int currentLevel = 0;
    private static final Stack<Pair<Integer, JSONObject>> info = new Stack<>();

    public static void startBuilding(Object obj) {
        if (obj.getClass().isAnnotationPresent(XmlRootElement.class)) {
            String name = obj.getClass().getAnnotation(XmlRootElement.class).name();
            List<Field> fields = getAllFields(obj);
            JSONObject json = new JSONObject();
            fields.forEach(item -> {
                if (item.isAnnotationPresent(XmlAttribute.class)) {
                    try {
                        Object value = item.get(obj);
                        if (value != null) {
                            json.put(item.getName(), value);
                        }
                    } catch (Exception e) {
                        // Cannot process the field
                    }
                }
            });
            json.put("CEITypeID", name);
            info.push(new Pair<>(currentLevel++, json));
        } else {
            // Cannot trace this item.
        }
    }

    private static List<Field> getAllFields(Object obj) {
        Class c = obj.getClass();
        List<Field> fieldList = new LinkedList<>();
        while (c != null) {
            fieldList.addAll(Arrays.asList(c.getDeclaredFields()));
            c = c.getSuperclass();
        }
        return fieldList;
    }

    public static String getTraceString() {
        StringBuilder stringBuilder = new StringBuilder();
        info.forEach(item -> {
            int level = item.getKey();
            for (int i = 0; i < level; i++) {
                stringBuilder.append("  ");
            }
            stringBuilder.append(item.getValue().toJSONString());
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }

    public static void endBuilding() {
        currentLevel--;
        info.pop();
    }
}
