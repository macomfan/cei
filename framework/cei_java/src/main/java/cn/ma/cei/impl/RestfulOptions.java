/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.impl;

/**
 *
 * @author u0151316
 */
public class RestfulOptions {
    public String url = null;
    public Integer connectionTimeout = null;
    public String apiKey = null;
    public String secretKey = null;
    
    public void setFrom(RestfulOptions options) {
        if (options.url != null) {
            this.url = options.url;
        }
        if (options.connectionTimeout != null) {
            this.connectionTimeout = options.connectionTimeout;
        }
        if (options.apiKey != null) {
            this.apiKey = options.apiKey;
        }
        if (options.secretKey != null) {
            this.secretKey = options.secretKey;
        }
    }
}
