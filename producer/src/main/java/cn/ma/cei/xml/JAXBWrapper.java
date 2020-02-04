package cn.ma.cei.xml;

import cn.ma.cei.exception.CEIException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class JAXBWrapper<T> {

    private Class<T> rootClass;

    public JAXBWrapper(Class<T> rootClass) {
        this.rootClass = rootClass;
        checkCurrentClass(rootClass, clsForLoadingJAXB);
    }

    private final static List<Class<?>> clsForLoadingJAXB = new LinkedList<>();

    private void checkCurrentClass(Class<?> cls, List<Class<?>> classes) {
        if (cls.getName().equals("java.lang.Object") || !cls.isAnnotationPresent(XmlRootElement.class)) {
            return;
        } else {
            if (!classes.contains(cls)) {
                classes.add(cls);
            } else {
                return;
            }
        }

        for (Field field : cls.getDeclaredFields()) {
            if (field.isAnnotationPresent(CEIXmlAnyElementTypes.class)) {
                for (Class<?> c : field.getAnnotation(CEIXmlAnyElementTypes.class).value()) {
                    if (!classes.contains(c)) {
                        checkCurrentClass(c, classes);
                    }
                }
            }
            if (field.getGenericType() instanceof ParameterizedType) {
                ParameterizedType type = (ParameterizedType) field.getGenericType();
                Type[] types = type.getActualTypeArguments();
                for (Type t : types) {
                    checkCurrentClass((Class<?>) t, classes);
                }
            } else {
                checkCurrentClass(field.getType(), classes);
            }
        }
        checkCurrentClass(cls.getSuperclass(), classes);
    }

    public T loadFromXML(File file) throws JAXBException {
        Class<?>[] tmp = clsForLoadingJAXB.toArray(new Class[clsForLoadingJAXB.size()]);
        JAXBContext context = JAXBContext.newInstance(tmp, null);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(file);

    }

    public List<T> loadFromFolder(String path) throws JAXBException {
        List<T> result = new LinkedList<>();
        File folder = new File(path);
        if (!folder.exists()) {
            throw new CEIException("Path not exist");
        } else {
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    result.addAll(loadFromFolder(file.getAbsolutePath()));
                } else {
                    if (".xml".equals(file.getName().substring(file.getName().lastIndexOf('.')).toLowerCase())) {
                        result.add(loadFromXML(file));
                    }
                }
            }
            return result;
        }
    }
}
