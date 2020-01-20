package cn.ma.cei;

import cn.ma.cei.finalizer.Finalizer;
import cn.ma.cei.generator.BuildSDK;
import cn.ma.cei.generator.environment.Environment;
import cn.ma.cei.generator.langs.cpp.CppFramework;
import cn.ma.cei.generator.langs.golang.GoFramework;
import cn.ma.cei.generator.langs.java.JavaFramework;
import cn.ma.cei.generator.langs.python3.Python3Framework;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.service.ProcessContext;
import cn.ma.cei.service.processors.ModelUpdateProcessor;
import cn.ma.cei.xml.JAXBWrapper;
import cn.ma.cei.xml.Convert;
import cn.ma.cei.xml.JsonToXml;
import cn.ma.cei.xml.XmlToJson;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.xml.bind.JAXBException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {

    int aaa = 0;

    static class models {
        public List<String> values = new LinkedList<>();
    }

    static class testcls {
        public String name = "abc";
        public models m = new models();
    }


    public static void main(String[] args) throws NoSuchFieldException, JAXBException {
        models ll = new models();
        ll.values.add("111");
        ll.values.add("222");
        testcls t = new testcls();
        t.m = ll;
        //JSONObject js = (JSONObject)JSON.toJSON(t);

        testcls newt = JSON.parseObject("{}", testcls.class);


        StringBuilder sb = new StringBuilder();
        sb.append("111111\r\n");
//        sb.append("222222\r\n");
//        sb.append("333333\r\n");
//        sb.append("\r\n");
        sb.append("\r\n");

        int from = 0;
        int index = 0;

        while (true) {
            index = sb.indexOf("\r\n", from);
            if (index == -1) {
                break;
            }
            String tmp = sb.substring(from, index);
            from = index + "\r\n".length();
        }

        String pattern = "\\{.*\\}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher("{aaa}");
        if (m.find()) {
            System.out.println("find");
        }

//        Code code = new Code();
//        code.appendln("import cn.macomfan.ccei.sdk.impl.RestfulConnection;");
//        code.endln();
//        code.appendln("public class " + "AAA" + " {");
//        code.newBlock(()->{
//            code.endln();
//            code.appendWordsln("private", "String", "mystring;");
//            code.endln();
//        });
//        code.appendln("}");
        JAXBWrapper wrapper = new JAXBWrapper();
        List<xSDK> sdks = wrapper.loadFromFolder("C:\\dev\\cei\\exchanges\\huobipro", xSDK.class);
        Finalizer finalizer = new Finalizer();
        finalizer.addSDK(sdks);
        List<xSDK> finalSDKs = finalizer.finalizeSDK();

        BuildSDK.registerFramework(Environment.Language.java, new JavaFramework());
        BuildSDK.registerFramework(Environment.Language.cpp, new CppFramework());
        BuildSDK.registerFramework(Environment.Language.python3, new Python3Framework());
        BuildSDK.registerFramework(Environment.Language.golang, new GoFramework());
//        BuildSDK.build(finalSDKs, Environment.Language.java, "C:\\dev\\cei\\output");
//        BuildSDK.build(finalSDKs, Environment.Language.python3, "C:\\dev\\cei\\output");
//        try {
            BuildSDK.build(finalSDKs, Environment.Language.java, "C:\\dev\\cei\\output");
//            BuildSDK.build(finalSDKs, Environment.Language.golang, "C:\\dev\\cei\\output");
//            BuildSDK.build(finalSDKs, Environment.Language.python3, "C:\\dev\\cei\\output");
//        } catch (Exception e) {
//            System.err.println(BuildTracer.getTraceString());
//        }
        XmlToJson xmlToJson = new XmlToJson();
        Convert.doConvert(xmlToJson, finalSDKs.get(0));
        System.out.println(xmlToJson.toJsonString());
        JSONObject jsonObject = JSONObject.parseObject(xmlToJson.toJsonString());
        JsonToXml jsonToXml = new JsonToXml(jsonObject);
        xSDK newSdk = new xSDK();
        Convert.doConvert(jsonToXml, newSdk);

        XmlToJson xmlToJson2 = new XmlToJson();
        Convert.doConvert(xmlToJson2, newSdk);
        System.out.println(xmlToJson2.toJsonString());
//        
//        
//        
//        BuildExchange.build(finalSDK.get(0), new CppExchangeBuilder());
//        BuildExchange.build(finalSDK.get(0), new JavaExchangeBuilder());
//        File file = new File("C:\\dev\\cei\\src\\main\\resources\\main_ex.xml");
//        xSDK sdk = wrapper.loadFromXML(file, xSDK.class);
//        System.out.println("--------");
//        JavaCodeBuilder builder = new JavaCodeBuilder();
//        builder.build(sdks.get(0));
//        System.out.println("--------");
        //XMLDecoder decoder = new XMLDecoder();
        //xSDK sdk = (xSDK) decoder.decode(document, xSDK.class);
        System.out.println("End");

//        Field f = main.class.getDeclaredField("aaa");
//        Type t = f.getGenericType();
//        System.out.println(f.getClass().getName());
//        int b = 0;
//        System.out.println(int.class.isInstance(b));
    }
}
