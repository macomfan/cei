/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.exception;

/**
 *
 * @author u0151316
 */
public enum CEIErrorType {
    PARSE("Parse"),
    RUNTIME("Runtime"),
    UNKNOWN("Unknown");

    private final String name;

    private CEIErrorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
