package cn.ma.cei.model;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "sdk")
public class xSDK extends xElement {

    @XmlAttribute(name = "exchange")
    public String exchange;

    @XmlAttribute(name = "document")
    public String document;

    @XmlElement(name = "model")
    public List<xModel> modelList;

    @XmlElement(name = "signature")
    public List<xSignature> signatureList;

    @XmlElement(name = "restful")
    public List<xRestful> restfulList;


    private <T> List<T> mergeList (List<T> dst, List<T> src) {
        if (dst == null) {
            dst = new LinkedList<>();
        }
        if (src != null) {
            dst.addAll(src);
        }
        return dst;
    }

    public void merge(xSDK sdk) {
        restfulList = mergeList(restfulList, sdk.restfulList);
        modelList = mergeList(modelList, sdk.modelList);
        signatureList =mergeList(signatureList, sdk.signatureList);
    }

    @Override
    public void check() {

    }
}
