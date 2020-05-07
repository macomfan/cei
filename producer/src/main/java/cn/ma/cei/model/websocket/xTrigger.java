package cn.ma.cei.model.websocket;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.json.xJsonChecker;
import cn.ma.cei.model.processor.xWebSocketNullChecker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "trigger")
public class xTrigger extends xElement {
    @XmlElement(name = "json_checker")
    public xJsonChecker jsonChecker;

    @XmlElement(name = "null_checker")
    public xWebSocketNullChecker nullChecker;

    @Override
    public void customCheck() {
        super.customCheck();
        if (jsonChecker != null && nullChecker != null) {
            CEIErrors.showXMLFailure("json_checker and null_checker cannot be defined in same time.");
        }
    }
}
