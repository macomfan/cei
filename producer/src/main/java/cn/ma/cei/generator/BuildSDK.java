/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.Framework;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.environment.Environment;
import cn.ma.cei.model.xSDK;
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

    public static class Request {

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

//        CEIPath folder = new CEIPath(CEIPath.Type.FOLDER, outputFolder);
//        if (!folder.exists()) {
//            throw new CEIException("[BuildSDK] The output folder does not exist");
//        }
//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
//        String date = df.format(new Date());
//        CEIPath buildFolder = CEIPath.appendPath(folder, date);
//        buildFolder.mkdirs();
//        CEIPath buildFolder = CEIPath.appendPath(folder, date);
//        buildFolder.mkdirs();
//        CEIPath frameworkPath = new CEIPath(CEIPath.Type.FOLDER, "C:\\dev\\cei\\framework");
//        frameworkPath.copyTo(buildFolder);
        CEIPath buildFolder = new CEIPath(CEIPath.Type.FOLDER, "C:\\dev\\cei\\framework");
        buildFolder.mkdirs();

        Framework framework = frameworks.get(language);

        Environment.setWorkingFolder(CEIPath.appendPath(buildFolder, framework.getFrameworkName()));

        sdks.forEach((sdk) -> {
            sdk.startBuilding();
            Environment.setCurrentExchange(sdk.exchange);
            Environment.setCurrentLanguage(language);
            Environment.setCurrentDescriptionConverter(framework.getDescriptionConverter());
            
            BuildExchange.build(sdk, framework.getExchangeBuilder());
            sdk.endBuilding();
        });
    }
}
