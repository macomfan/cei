package cn.ma.cei.model.restful;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.xPreProcessor;
import cn.ma.cei.utils.Checker;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
public class xRequest extends xElement {

    @XmlAttribute(name = "method")
    public String method;

    @XmlAttribute(name = "target")
    public String target;

    @XmlElement(name = "authentication")
    public xAuthentication authentication;

    @XmlElementWrapper(name = "headers")
    @XmlElement(name = "header")
    public List<xHeader> headers;

    @XmlElementWrapper(name = "query_strings")
    @XmlElement(name = "query")
    public List<xQuery> queryStrings;

    @XmlElement(name = "post_body")
    public xPostBody postBody;

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
