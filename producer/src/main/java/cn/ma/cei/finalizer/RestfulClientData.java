package cn.ma.cei.finalizer;

import cn.ma.cei.model.restful.xConnection;
import cn.ma.cei.model.restful.xInterface;
import cn.ma.cei.utils.UniqueList;

public class RestfulClientData {
    private String name;
    private xConnection connection;
    private UniqueList<String, xInterface> interfaces = new UniqueList<>();
}
