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
@FunctionalInterface
public interface IWebSocketCallback<T> {
    void invoke(T data);
}
