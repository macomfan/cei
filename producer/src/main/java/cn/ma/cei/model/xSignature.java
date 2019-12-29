/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.model;

import cn.ma.cei.model.base.xSignatureItem;
import cn.ma.cei.model.signature.xAppendQueryString;
import cn.ma.cei.model.signature.xCombineQueryString;
import cn.ma.cei.model.signature.xGetMethod;
import cn.ma.cei.model.signature.xGetNow;
import cn.ma.cei.xml.XmlAnyElementTypes;
import java.util.List;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author U0151316
 */
@XmlRootElement(name = "signature")
public class xSignature {

    @XmlAnyElement(lax = true)
    @XmlAnyElementTypes({
        xGetNow.class,
        xAppendQueryString.class,
        xCombineQueryString.class,
        xGetMethod.class})
    public List<xSignatureItem> items;
}
