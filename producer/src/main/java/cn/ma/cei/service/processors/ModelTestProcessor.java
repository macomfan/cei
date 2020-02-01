/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.service.processors;

import cn.ma.cei.service.ProcessContext;
import cn.ma.cei.service.messages.ModelTestMessage;
import cn.ma.cei.service.response.NullResponse;

/**
 *
 * @author u0151316
 */
public class ModelTestProcessor extends ProcessorBase<ModelTestMessage> {

    @Override
    public String getType() {
        return ProcessorBase.REQ;
    }

    @Override
    public String getCatalog() {
        return "ModTest";
    }

    @Override
    public ModelTestMessage newMessage() {
        return new ModelTestMessage();
    }

    @Override
    public void process(ProcessContext context, ModelTestMessage msg) {
        context.response(new NullResponse());
    }

}
