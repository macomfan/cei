/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.SignatureBuilder;
import cn.ma.cei.model.signature.xAppendQueryString;
import cn.ma.cei.model.signature.xGetNow;
import cn.ma.cei.model.xSignature;

/**
 *
 * @author U0151316
 */
public class BuildSignature {

    public void build(xSignature signature, SignatureBuilder builder) {
        signature.items.forEach((item) -> {
            if (item instanceof xGetNow) {
                
            } else if (item instanceof xAppendQueryString) {
                
            }
        });
    }
}
