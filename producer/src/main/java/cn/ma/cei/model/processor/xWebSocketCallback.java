package cn.ma.cei.model.processor;

import cn.ma.cei.model.base.xDataProcessorItem;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "invoke_callback")
public class xWebSocketCallback extends xDataProcessorItem {
    public String func;

    public String argument;
}
