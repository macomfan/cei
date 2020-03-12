package cn.ma.cei.model.restful;


import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.base.xType;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xDecimal;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.xResponse;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlType(name = "interface_restful")
@XmlRootElement(name = "interface")
public class xInterface extends xElement {

    @XmlAttribute(name = "name")
    public String name;

    @XmlElementWrapper(name = "inputs")
    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({xString.class, xBoolean.class, xInt.class, xDecimal.class})
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
