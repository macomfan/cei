package cn.ma.cei.xml;

import cn.ma.cei.exception.CEIException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class JAXBWrapper {

    private void checkCurrentClass(Class cls, List<Class> classes) {
        if (cls.getName().equals("java.lang.Object")) {
            return;
        }
        if (!cls.isAnnotationPresent(XmlRootElement.class) && !cls.isAnnotationPresent(XmlElementBase.class)) {
            return;
        }
        if (cls.isAnnotationPresent(XmlRootElement.class)) {
            if (!classes.contains(cls)) {
                classes.add(cls);
            }
        }

        for (Field field : cls.getDeclaredFields()) {
            if (field.isAnnotationPresent(XmlAnyElementTypes.class)) {
                for (Class c : field.getAnnotation(XmlAnyElementTypes.class).value()) {
                    if (!classes.contains(c)) {
                        classes.add(c);
                    }
                }
            }
            if (field.getGenericType() instanceof ParameterizedType) {
                ParameterizedType type = (ParameterizedType) field.getGenericType();
                Type[] types = type.getActualTypeArguments();
                for (Type t : types) {
                    checkCurrentClass((Class) t, classes);
                }
            } else {
                checkCurrentClass(field.getType(), classes);
            }
        }
        checkCurrentClass(cls.getSuperclass(), classes);
    }

    public <T extends Object> T loadFromXML(File file, Class<T> rootClass) throws JAXBException {
        List<Class> classes = new LinkedList<>();
        checkCurrentClass(rootClass, classes);
        Class[] tmp = classes.toArray(new Class[classes.size()]);
        JAXBContext context = JAXBContext.newInstance(tmp, null);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(file);

    }

    public <T extends Object> List<T> loadFromFolder(String path, Class<T> rootClass) throws JAXBException {
        List<T> result = new LinkedList<>();
        File folder = new File(path);
        if (!folder.exists()) {
            throw new CEIException("Path not exist");
        } else {
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    result.addAll(loadFromFolder(file.getAbsolutePath(), rootClass));
                } else {
                    if (".xml".equals(file.getName().substring(file.getName().lastIndexOf('.')).toLowerCase())) {
                        result.add(loadFromXML(file, rootClass));
                    }
                }
            }
            return result;
        }
    }
}
