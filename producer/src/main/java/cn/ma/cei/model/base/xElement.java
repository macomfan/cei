package cn.ma.cei.model.base;


import cn.ma.cei.exception.BuildTracer;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.exception.CEIXmlException;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.ReflectionHelper;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;
import cn.ma.cei.xml.CEIXmlAnyElementTypesExtension;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "")
public abstract class xElement {

    public String filename;

    @FunctionalInterface
    public interface Building {
        void doBuild();
    }

    public void startBuilding() {
        BuildTracer.startBuilding(this);
    }

    public void endBuilding() {
        BuildTracer.endBuilding();
    }

    public void doBuild(Building building) {
        BuildTracer.startBuilding(this);
        building.doBuild();
        BuildTracer.endBuilding();
    }

    public String getXMLName() {
        XmlRootElement xmlRootElement = this.getClass().getAnnotation(XmlRootElement.class);
        return xmlRootElement.name();
    }

    public void doCheck() {
        startBuilding();
        if (!this.getClass().isAnnotationPresent(XmlRootElement.class)) {
            CEIErrors.showCodeFailure(this.getClass(), "%s must define XmlRootElement annotation.", this.getClass().getName());
        }
        customCheck();
        ReflectionHelper.getAllFields(this).forEach(field -> {
            if (field.isAnnotationPresent(XmlElement.class)) {
                // Check for normal element, the element can be list or single instance.
                if (field.getType().getName().equals(List.class.getName())) {
                    List<?> list = ReflectionHelper.getFieldValue(field, this, List.class);
                    if (list != null) {
                        list.forEach(item -> {
                            if (!(item instanceof xElement)) {
                                CEIErrors.showCodeFailure(this.getClass(), "%s must inherit from xElement.", item.getClass().getName());
                            } else {
                                ((xElement) item).filename = this.filename;
                                ((xElement) item).doCheck();
                            }
                        });
                    }
                } else if (xElement.class.isAssignableFrom(field.getType())) {
                    xElement element = ReflectionHelper.getFieldValue(field, this, xElement.class);
                    if (element != null) {
                        element.filename = this.filename;
                        element.doCheck();
                    }
                }
            } else if (field.isAnnotationPresent(XmlAnyElement.class)) {
                // Check for any element, it must be list.
                if (!field.isAnnotationPresent(CEIXmlAnyElementTypes.class)) {
                    CEIErrors.showCodeFailure(this.getClass(), "Must define CEIXmlAnyElementTypes for %s", field.getName());
                }
                if (!field.getType().getName().equals(List.class.getName())) {
                    CEIErrors.showCodeFailure(this.getClass(), "Must define as a List for %s", field.getName());
                }
                Class<?>[] classes1 = field.getAnnotation(CEIXmlAnyElementTypes.class).classes();
                List<Class<?>> classesAll = new LinkedList<>();
                Collections.addAll(classesAll, classes1);
                if (this.getClass().isAnnotationPresent(CEIXmlAnyElementTypesExtension.class)) {
                    CEIXmlAnyElementTypesExtension extension = this.getClass().getAnnotation(CEIXmlAnyElementTypesExtension.class);
                    if (field.getName().equals(extension.fieldName())) {
                        Class<?>[] classes2 = extension.classes();
                        Collections.addAll(classesAll, classes2);
                    }
                }
                List<?> list = ReflectionHelper.getFieldValue(field, this, List.class);
                if (list != null) {
                    list.forEach(item -> {
                        if (!(item instanceof xElement)) {
                            CEIErrors.showCodeFailure(this.getClass(), "%s must inherit from xElement.", item.getClass().getName());
                        } else if (!Checker.checkInstanceOf(item, classesAll)) {
                            CEIErrors.showCodeFailure(this.getClass(), "%s must be the class defined in CEIXmlAnyElementTypes for %s", item.getClass().getName(), field.getName());
                        } else {
                            ((xElement) item).filename = this.filename;
                            ((xElement) item).doCheck();
                        }
                    });
                }
            } else if (field.isAnnotationPresent(XmlAttribute.class)) {
                // check for attribute.
                XmlAttribute attribute = field.getAnnotation(XmlAttribute.class);
                if (attribute.required() && ReflectionHelper.getFieldValue(field, this, Object.class) == null) {
                    CEIErrors.showXMLFailure("%s must be defined.", field.getName());
                }
            }
        });
        endBuilding();
    }

    public void checkMemberNotNull(String member, String memberName) {
        if (Checker.isEmpty(member)) {
            throw new CEIException(memberName + " is null");
        }
    }

    public <T extends xElement> void checkMemberNotNull(T member, String memberName) {
        if (member == null) {
            throw new CEIXmlException(this.getClass(), memberName + " is null");
        }
    }

    /**
     * Custom check for each XML element.
     * Do not call customCheck() for the children element.
     *
     */
    public void customCheck() {

    }
}
