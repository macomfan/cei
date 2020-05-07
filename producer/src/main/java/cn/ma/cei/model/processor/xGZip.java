package cn.ma.cei.model.processor;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.test.websocket.notification.PingPong;
import cn.ma.cei.utils.Checker;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "gzip")
public class xGZip extends xDataProcessorItem {
    public static final String DECOMPRESS = "decompress";
    public static final String COMPRESS = "compress";

    @XmlAttribute(name = "name", required = true)
    public String name;

    @XmlAttribute(name = "input", required = true)
    public String input;

    @XmlAttribute(name = "type", required = true)
    public String type;

    @Override
    public void customCheck() {
        super.customCheck();
        if (Checker.isEmpty(type)) {
            type = DECOMPRESS;
        } else {
            if (!Checker.valueIn(type, DECOMPRESS, DECOMPRESS)) {
                CEIErrors.showXMLFailure("gzip type is invalid");
            }
        }
    }
}
