package cn.ma.cei.exception;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlRootElement;

public class CEIXmlException extends RuntimeException {
    public CEIXmlException(Class<? extends xElement> cls, String msg) {
        super("[" + cls.getAnnotation(XmlRootElement.class).name() + "] " + msg + "\n" + BuildTracer.getTraceString());
    }
}
