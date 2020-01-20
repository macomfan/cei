package cn.ma.cei.service.processors;

import cn.ma.cei.service.ProcessContext;

public interface IProcessor<T> {
    void process(ProcessContext context, T msg);
}
