package cn.ma.cei.model.base;


import cn.ma.cei.exception.*;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.ReflectionHelper;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.List;

@XmlRootElement(name = "")
public abstract class xElement {

    public String filename;

    @FunctionalInterface
    public interface Building {
        void doBuild();
    }

    @FunctionalInterface
    public interface BuildingWithReturn<T> {
        T doBuild();
    }

    public void startBuilding() {
        BuildTracer.startBuilding(this);
    }

    public void endBuilding() {
        BuildTracer.endBuilding();
    }

    public <T> T doBuildWithReturn(BuildingWithReturn<T> building) {
        startBuilding();
        T res = building.doBuild();
        endBuilding();
        return res;
    }

    public void doBuild(Building building) {
        BuildTracer.startBuilding(this);
        building.doBuild();
        BuildTracer.endBuilding();
    }

    public void doCheck() {
        startBuilding();
        // TODO need check ElementNSImpl
        if (!this.getClass().isAnnotationPresent(XmlRootElement.class)) {
            CEIErrors.showFailure(CEIErrorType.CODE, this.getClass().getName() + " must define XmlRootElement annotation.");
        }

        customCheck();
        if (this.getClass().getName().equals("cn.ma.cei.model.restful.xAuthentication")) {
            int a = 0;
        }
        List<Field> fff = ReflectionHelper.getAllFields(this);
        ReflectionHelper.getAllFields(this).forEach(field -> {
            if (field.isAnnotationPresent(XmlElement.class) || field.isAnnotationPresent(XmlAnyElement.class)) {
                if (field.getType().getName().equals(List.class.getName())) {
                    List<?> list = ReflectionHelper.getFieldValue(field, this, List.class);
                    if (list != null) {
                        list.forEach(item -> {
                            if (!(item instanceof xElement)) {
                                CEIErrors.showFailure(CEIErrorType.CODE, item.getClass().getName() + " must inherit from xElement.");
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
                } else {
                    CEIErrors.showFailure(CEIErrorType.CODE, field.getType().getName() + " must inherit from xElement.");
                }
            } else if (field.isAnnotationPresent(XmlAttribute.class)) {
                XmlAttribute attribute = field.getAnnotation(XmlAttribute.class);
                if (attribute.required()) {
                    if (ReflectionHelper.getFieldValue(field, this, Object.class) == null) {
                        CEIErrors.showFailure(CEIErrorType.XML, "\"%s\" in %s must be defined.", field.getName(), this.getClass().getName());
                    }
                }
            }
        });
        endBuilding();
//        try {
//            doBuild(this::customCheck);
//        } catch (CEIException e) {
//            throw e;
//        } catch (Exception e) {
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            e.printStackTrace(pw);
//            CEIErrors.showFailure(CEIErrorType.UNKNOWN, "Exception: %s\n%s", e.getMessage(), sw.toString());
//        }
    }

    public <T extends xElement> void checkMember(T member) {
        if (member != null) {
            member.doCheck();
        }
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

    public void customCheck() {

    }
}
