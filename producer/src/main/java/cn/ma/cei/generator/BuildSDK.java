/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.Framework;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.environment.Environment;
import cn.ma.cei.generator.langs.cpp.CppExchangeBuilder;
import cn.ma.cei.generator.langs.java.JavaExchangeBuilder;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.utils.FileCopy;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author u0151316
 */
public class BuildSDK {

    public class Request {

        public String outputFolder = "C:\\dev\\cei\\output";
        public boolean addTimestamp = true;
    }

    public static Map<Environment.Language, Framework> frameworks = new HashMap<>();

    public static void registerFramework(Environment.Language language, Framework framework) {
        if (frameworks.containsKey(language)) {
            throw new CEIException("[BuildSDK] Framework duplicated");
        }
        frameworks.put(language, framework);
    }

    public static void build(List<xSDK> sdks, Environment.Language language, String outputFolder) {
        if (!frameworks.containsKey(language)) {
            throw new CEIException("[BuildSDK] The framework does not exist");
        }

        File folder = new File(outputFolder);
        if (!folder.exists()) {
            throw new CEIException("[BuildSDK] The output folder does not exist");
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");//??????
        String date = df.format(new Date());// new Date()???????????????????
        File buildFolder = new File(folder.getPath() + "/" + date);
        buildFolder.mkdir();
        
        FileCopy.folderCopy(new File("C:\\dev\\cei\\framework"), buildFolder);
        Framework framework = frameworks.get(language);
        
        Environment.setWorkingFolder(new File(buildFolder.getPath() + File.separator + framework.getFrameworkName()));
        
        sdks.forEach((sdk) -> {
            BuildExchange.build(sdk, framework.getExchangeBuilder());
        });

//        sdks.forEach((sdk) -> {
//            switch (language) {
//                case java:
//                    BuildExchange.build(sdk, new JavaExchangeBuilder());
//                    break;
//                case cpp:
//                    BuildExchange.build(sdk, new CppExchangeBuilder());
//                    break;
//            }
//        });
    }

}
