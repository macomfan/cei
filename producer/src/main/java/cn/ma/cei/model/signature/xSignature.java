/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.model.signature;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.base.xSignatureItem;
import cn.ma.cei.model.signature.xAddQueryString;
import cn.ma.cei.model.signature.xAddStringArray;
import cn.ma.cei.model.signature.xAppendToString;
import cn.ma.cei.model.signature.xBase64;
import cn.ma.cei.model.signature.xCombineQueryString;
import cn.ma.cei.model.signature.xCombineStringArray;
import cn.ma.cei.model.signature.xGetRequestInfo;
import cn.ma.cei.model.signature.xGetNow;
import cn.ma.cei.model.signature.xHmacsha256;

import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import java.util.List;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author U0151316
 */
@XmlRootElement(name = "signature")
public class xSignature extends xElement {

    @XmlAttribute(name = "name")
    public String name;

    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({
            xGetNow.class,
            xAddQueryString.class,
            xCombineQueryString.class,
            xGetRequestInfo.class,
            xAddStringArray.class,
            xAppendToString.class,
            xCombineStringArray.class,
            xBase64.class,
            xHmacsha256.class})
    public List<xSignatureItem> items;

    @Override
    public void customCheck() {
        super.customCheck();
        checkMemberNotNull(name, "name");
        checkMember(items);
    }
}
