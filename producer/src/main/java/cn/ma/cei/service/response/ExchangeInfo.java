package cn.ma.cei.service.response;

import java.util.LinkedList;
import java.util.List;

public class ExchangeInfo implements IResponse {
    public static class Client {
        public String name;
        public List<String> interfaces = new LinkedList<>();
    }

    public String name;
    public List<String> models = new LinkedList<>();
    public List<Client> clients = new LinkedList<>();
}
