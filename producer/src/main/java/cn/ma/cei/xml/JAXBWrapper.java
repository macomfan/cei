package cn.ma.cei.xml;

import cn.ma.cei.exception.CEIException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class JAXBWrapper<T> {

    private Set<Class<?>> clsForLoadingJAXB = new HashSet<>();
    private Class<T> rootClass;

    public JAXBWrapper(Class<T> rootClass) {
        this.rootClass = rootClass;
        clsForLoadingJAXB.add(rootClass);
        Set<Class<?>> checkedClasses = new HashSet<>();
        checkCurrentClass(rootClass, checkedClasses);
    }

    private void checkCurrentClass(Class<?> cls, Set<Class<?>> checkedClasses) {
        if (checkedClasses.contains(cls)) {
            return;
        }
        checkedClasses.add(cls);
        if (cls.getName().equals("java.lang.Object") || !cls.isAnnotationPresent(XmlRootElement.class)) {
            return;
        }
        for (Field field : cls.getDeclaredFields()) {
            if (field.isAnnotationPresent(CEIXmlAnyElementTypes.class)) {
                for (Class<?> c : field.getAnnotation(CEIXmlAnyElementTypes.class).value()) {
                    if (!clsForLoadingJAXB.contains(c)) {
                        clsForLoadingJAXB.add(c);
                        checkCurrentClass(c, checkedClasses);
                    }
                }
            }
            if (field.getGenericType() instanceof ParameterizedType) {
                ParameterizedType type = (ParameterizedType) field.getGenericType();
                Type[] types = type.getActualTypeArguments();
                for (Type t : types) {
                    checkCurrentClass((Class<?>) t, checkedClasses);
                }
            } else {
                checkCurrentClass(field.getType(), checkedClasses);
            }
        }
        checkCurrentClass(cls.getSuperclass(), checkedClasses);
    }

    public T loadFromXML(File file) {
        try {
            Class<?>[] tmp = clsForLoadingJAXB.toArray(new Class[0]);
            JAXBContext context = JAXBContext.newInstance(tmp, null);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return rootClass.cast(unmarshaller.unmarshal(file));
        } catch (Exception e) {
            System.err.println(file.getPath());
            System.err.println(e.getCause().getMessage());
        }
        return null;
    }

    public List<T> loadFromFolder(String path) {
        List<T> result = new LinkedList<>();
        File folder = new File(path);
        if (!folder.exists()) {
            throw new CEIException("Path not exist");
        } else {
            File[] files = folder.listFiles();
            if (files == null) {
                return result;
            }
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
