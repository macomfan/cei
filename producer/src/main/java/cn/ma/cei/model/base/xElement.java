package cn.ma.cei.model.base;


import cn.ma.cei.exception.BuildTracer;
import cn.ma.cei.exception.CEIXmlException;
import cn.ma.cei.utils.Checker;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "")
public abstract class xElement {
    public void startBuilding() {
        BuildTracer.startBuilding(this);
    }

    public void endBuilding() {
        BuildTracer.endBuilding();
    }

    public void doCheck() {
        startBuilding();
        customCheck();
        endBuilding();
    }

    public <T extends xElement> void checkMember(T member) {
        if (member != null) {
            member.doCheck();
        }
    }

    public <V extends xElement, T extends List<V>> void checkMember(T member) {
        if (member != null) {
            member.forEach(xElement::doCheck);
        }
    }

    public <V extends xElement, T extends List<V>> void checkMemberNotNull(T member, String memberName) {
        if (member == null || member.isEmpty()) {
            throw new CEIXmlException(this.getClass(), memberName + " is null");
        }
        checkMember(member);
    }

    public void checkMemberNotNull(String member, String memberName) {
        if (Checker.isEmpty(member)) {
            throw new CEIXmlException(this.getClass(), memberName + " is null");
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
