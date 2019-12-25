/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.environment;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.CEIPath;

/**
 *
 * @author u0151316
 */
public class Environment {

    public enum Language {
        java,
        cpp,
        python3,
        NA,
    }

    private static CEIPath workingFolder = null;
    private static String currentExchange = "";
    private static Language currentLanguage = Language.NA;

    public static void setWorkingFolder(CEIPath folder) {
        if (!folder.exists()) {
            throw new CEIException("Folder invalid");
        }
        Environment.workingFolder = folder;
    }

    public static CEIPath getWorkingFolder() {
        return workingFolder;
    }

    public static String getCurrentExchange() {
        return Environment.currentExchange;
    }

    public static void setCurrentExchange(String exchange) {
        Environment.currentExchange = exchange;
    }

    public static Language getCurrentLanguage() {
        return Environment.currentLanguage;
    }

    public static void setCurrentLanguage(Language language) {
        Environment.currentLanguage = language;
    }

}
