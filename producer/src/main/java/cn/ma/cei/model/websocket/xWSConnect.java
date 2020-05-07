package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElementWithInputs;
import cn.ma.cei.model.xPreProcessor;
import cn.ma.cei.utils.Checker;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "connect")
public class xWSConnect extends xElementWithInputs {

    @XmlAttribute
    public String target;

    @XmlElement(name = "pre_processor")
    public xPreProcessor preProcessor;

    @Override
    public void customCheck() {
        super.customCheck();
        if (!Checker.isEmpty(target)) {
            target = target.replace('\\', '/');
            if (!target.startsWith("/")) {
                target = "/" + target;
            }
        }
    }
}
