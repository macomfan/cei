/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.exception;

import cn.ma.cei.utils.NormalMap;
import cn.ma.cei.utils.ReflectionHelper;
import io.vertx.core.json.JsonObject;
import javafx.util.Pair;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Stack;

/**
 * @author u0151316
 */
public class BuildTracer {
    private static final Stack<Pair<Integer, ITraceFormat>> info = new Stack<>();
    private static int currentLevel = 0;

    public static void startBuilding(Object obj) {
        if (obj.getClass().isAnnotationPresent(XmlRootElement.class)) {
            String name = obj.getClass().getAnnotation(XmlRootElement.class).name();
            List<Field> fields = ReflectionHelper.getAllFields(obj);
            XMLTraceFormat xmlTraceFormat = new XMLTraceFormat();
            fields.forEach(item -> {
                if (item.isAnnotationPresent(XmlAttribute.class)) {
                    try {
                        Object value = item.get(obj);
                        if (value != null) {
                            xmlTraceFormat.addAttribute(item.getName(), value.toString());
                        }
                    } catch (Exception e) {
                        // Cannot process the field
                    }
                }
            });
            xmlTraceFormat.setElementName(name);
            info.push(new Pair<>(currentLevel++, xmlTraceFormat));
        } else {
            // Cannot trace this item.
        }
    }

    public static String getTraceString() {
        StringBuilder stringBuilder = new StringBuilder();
        info.forEach(item -> {
            int level = item.getKey();
            for (int i = 0; i < level; i++) {
                stringBuilder.append("  ");
            }
            stringBuilder.append(item.getValue().getTraceString());
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }

    public static void endBuilding() {
        currentLevel--;
        info.pop();
    }

    private interface ITraceFormat {
        String getTraceString();

        String setElementName(String elementName);

        void addAttribute(String key, String value);
    }

    private static class XMLTraceFormat implements ITraceFormat {
        private String elementName = "";
        private NormalMap<String, String> attrMap = new NormalMap<>();

        @Override
        public String getTraceString() {
            StringBuilder sb = new StringBuilder();
            sb.append("<");
            sb.append(elementName);
            attrMap.entrySet().forEach(item -> {
                sb.append(" ");
                sb.append(item.getKey());
                sb.append("=\"");
                sb.append(item.getValue());
                sb.append("\"");
            });
            sb.append(">");
            return sb.toString();
        }

        @Override
        public String setElementName(String elementName) {
            return this.elementName = elementName;
        }

        @Override
        public void addAttribute(String key, String value) {
            attrMap.put(key, value);
        }
    }

    private static class JsonTraceFormat implements ITraceFormat {
        private String elementName = "";
        private NormalMap<String, String> attrMap = new NormalMap<>();

        @Override
        public String getTraceString() {
            JsonObject json = new JsonObject();
            JsonObject attr = new JsonObject();
            attrMap.entrySet().forEach(item -> {
                attr.put(item.getKey(), item.getValue());
            });
            json.put(elementName, attr);
            return json.toString();
        }

        @Override
        public String setElementName(String elementName) {
            return this.elementName = elementName;
        }

        @Override
        public void addAttribute(String key, String value) {
            attrMap.put(key, value);
        }
    }
}
