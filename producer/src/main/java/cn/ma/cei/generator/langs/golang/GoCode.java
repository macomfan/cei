/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.Code;

/**
 *
 * @author U0151316
 */
public class GoCode extends Code{
    public void addPackage(String packageName) {
        appendWordsln("package", packageName);
    }
    
    
    public void addMemberln(String memberName, String memberType, int alignIndex) {
        StringBuilder line = new StringBuilder();
        line.append(memberName);
        if (memberName.length() <= alignIndex) { 
            int blankNum = alignIndex - memberName.length();
            line.append(String.format("%0" + blankNum + "d", 0).replace("0"," "));
            line.append(memberType);
        } else {
            throw new CEIException("[GoCode] addMemberln");
        }
        appendln(line.toString());
    }
}
