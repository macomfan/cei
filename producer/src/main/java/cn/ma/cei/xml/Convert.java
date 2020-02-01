package cn.ma.cei.xml;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.utils.ReflectionHelper;
import com.alibaba.fastjson.JSONObject;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class Convert {
    public static void doConvert(IXmlJsonConverter converter, Object xmlObject) {
        if(!xmlObject.getClass().isAnnotationPresent(XmlRootElement.class)) {
            throw new CEIException("[XMLToJson] XmlRootElement is not define");
        }
        converter.objectType(xmlObject);
        List<Field> fields = ReflectionHelper.getAllFields(xmlObject);
        fields.forEach((item) -> {
            if(item.isAnnotationPresent(XmlAttribute.class)) {
                converter.doAttribute(xmlObject, item);
            } else if (item.isAnnotationPresent(XmlElement.class) || item.isAnnotationPresent(XmlAnyElement.class)) {
                String name = item.getName();
                if (item.getType().getTypeName().equals(List.class.getTypeName())) {
                    converter.doList(xmlObject, item);
                } else {
                    converter.doObject(xmlObject, item);
                }
            }
        });
    }
}
