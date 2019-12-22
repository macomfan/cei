/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.environment;

import cn.ma.cei.generator.naming.DescriptionConverterDefault;
import cn.ma.cei.generator.naming.IDescriptionConverter;

/**
 *
 * @author u0151316
 */
public class Naming {
    public static IDescriptionConverter get() {
        return new DescriptionConverterDefault();
    }
}
