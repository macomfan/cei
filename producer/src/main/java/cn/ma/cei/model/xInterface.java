package cn.ma.cei.model;


import cn.ma.cei.model.base.xType;
import cn.ma.cei.model.base.xVarious;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "interface")
public class xInterface extends xVarious {

    @XmlAttribute(name = "name")
    public String name;

    @XmlElementWrapper(name = "inputs")
    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({xString.class, xBoolean.class, xInt.class})
    public List<xType> inputList;

    @XmlElement(name = "request")
    public xRequest request;

    @XmlElement(name = "response")
    public xResponse response;

    @Override
    public void customCheck() {
        super.customCheck();
        checkMemberNotNull(name, "name");
        checkMember(inputList);
        checkMemberNotNull(request, "request");
        checkMemberNotNull(response, "response");
    }
}
