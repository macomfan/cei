package cn.ma.cei;

import cn.ma.cei.finalizer.Finalizer;
import cn.ma.cei.generator.BuildExchange;
import cn.ma.cei.generator.langs.cpp.CppExchangeBuilder;
import cn.ma.cei.generator.langs.java.JavaExchangeBuilder;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.xml.JAXBWrapper;

import javax.xml.bind.JAXBException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {
    int aaa = 0;

    static class base {
        public static String aaa;
    }
    
    static class D1<T> extends base {
        
    }
    
    static class D2<T> extends base {
        
    }
    
    public static void main(String[] args) throws NoSuchFieldException, JAXBException {
        D1<Integer> di = new D1<>();
        D2<String> ds = new D2<>();
        di.aaa = "aaa";
        System.err.println(ds.aaa);
        
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
        List<xSDK> sdks = wrapper.loadFromFolder("C:\\dev\\cei\\config", xSDK.class);
        Finalizer finalizer = new Finalizer();
        finalizer.addSDK(sdks);
        List<xSDK> finalSDK = finalizer.finalizeSDK();
        BuildExchange.build(finalSDK.get(0), new CppExchangeBuilder());
        BuildExchange.build(finalSDK.get(0), new JavaExchangeBuilder());

//        File file = new File("C:\\dev\\cei\\src\\main\\resources\\main_ex.xml");
//        xSDK sdk = wrapper.loadFromXML(file, xSDK.class);

//        System.out.println("--------");
//        JavaCodeBuilder builder = new JavaCodeBuilder();
//        builder.build(sdks.get(0));
//        System.out.println("--------");

        //XMLDecoder decoder = new XMLDecoder();
        //xSDK sdk = (xSDK) decoder.decode(document, xSDK.class);
        System.out.println("End");

        Field f = main.class.getDeclaredField("aaa");
        Type t = f.getGenericType();
        System.out.println(f.getClass().getName());
        int b = 0;
        System.out.println(int.class.isInstance(b));
    }
}
