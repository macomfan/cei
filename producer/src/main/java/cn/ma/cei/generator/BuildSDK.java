/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.finalizer.Finalizer;
import cn.ma.cei.generator.builder.IFramework;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.utils.MapWithValue2;
import cn.ma.cei.xml.JAXBWrapper;

import java.util.List;

/**
 * @author u0151316
 */
public class BuildSDK {

    public static class Request {

        public String outputFolder = "C:\\dev\\cei\\output";
        public boolean addTimestamp = true;
    }

    public final static MapWithValue2<String, Language, IFramework> frameworks = new MapWithValue2<>();

    public static void registerFramework(IFramework framework) {
        Language language = framework.getLanguage();
        if (frameworks.containsKey(language.getName())) {
            throw new CEIException("[BuildSDK] Framework duplicated");
        }
        frameworks.put(language.getName(), language, framework);
    }

    public static void build(String inputFolder, String language, String outputFolder) {
        System.out.println("-- Load start");
        List<xSDK> sdks = (new JAXBWrapper()).loadFromFolder("C:\\dev\\cei\\exchanges");
        System.out.println("-- Load done");
        Finalizer finalizer = new Finalizer();
        finalizer.addSDK(sdks);
        List<xSDK> finalSDKs = finalizer.finalizeSDK();


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

        IFramework framework = frameworks.get2(language);
        GlobalContext.setWorkingFolder(CEIPath.appendPath(buildFolder, frameworks.get1(language).getWorkingName()));

        finalSDKs.forEach((sdk) -> sdk.doBuild(() -> {
            GlobalContext.setCurrentExchange(sdk.name);
            GlobalContext.setCurrentLanguage(frameworks.get1(language));
            GlobalContext.setCurrentFramework(framework);
            BuildExchange.build(sdk, framework.createExchangeBuilder());
        }));
    }
}
