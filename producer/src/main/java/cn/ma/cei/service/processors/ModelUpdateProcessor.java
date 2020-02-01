package cn.ma.cei.service.processors;

import cn.ma.cei.model.xModel;
import cn.ma.cei.service.ProcessContext;
import cn.ma.cei.service.messages.ModelUpdateMessage;
import cn.ma.cei.xml.Convert;
import cn.ma.cei.xml.JsonToXml;

public class ModelUpdateProcessor extends ProcessorBase<ModelUpdateMessage> {
    @Override
    public String getType() {
        return ProcessorBase.REQ;
    }

    @Override
    public String getCatalog() {
        return "ModUpt";
    }

    @Override
    public ModelUpdateMessage newMessage() {
        return new ModelUpdateMessage();
    }

    @Override
    public void process(ProcessContext context, ModelUpdateMessage msg) {
        JsonToXml jsonToXml = new JsonToXml(msg.param.data);
        xModel updatedModel = new xModel();
        Convert.doConvert(jsonToXml, updatedModel);
        int a = 0;
    }
}
