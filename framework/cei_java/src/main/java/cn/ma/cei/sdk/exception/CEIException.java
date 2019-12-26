/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.sdk.exception;

/**
 *
 * @author u0151316
 */
public class CEIException extends RuntimeException {
    public CEIException(String errMsg) {
        super(errMsg);
    }
}
