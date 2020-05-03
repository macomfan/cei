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
public class WebSocketOptions {
    public String url;
    public String apiKey = null;
    public String secretKey = null;
    public int connectTimeout_s = 10;

    public void setFrom(WebSocketOptions options) {
        if (options.url != null) {
            this.url = options.url;
        }
        if (options.apiKey != null) {
            this.apiKey = options.apiKey;
        }
        if (options.secretKey != null) {
            this.secretKey = options.secretKey;
        }
    }
}
