/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sina.weibo.toolbox.util.console;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author X-Spirit
 */
public class CmdStatus {

    private String status;
    private int code;
    private ArrayList<Thread> threads;
    private static CmdStatus instance;

    public static CmdStatus getInstance() {
        instance = new CmdStatus();
        return instance;
    }

    public static CmdStatus getInstance(int code) {
        instance = new CmdStatus(code);
        return instance;
    }

    private CmdStatus() {
        code = 0;
    }

    private CmdStatus(int code) {
        this.code = code;
    }

    public ArrayList<Thread> getThreads() {
        return threads;
    }

    public void setThreads(ArrayList<Thread> threads) {
        this.threads = threads;
    }

    public int getCode() {
        return code;
    }

    public synchronized void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
