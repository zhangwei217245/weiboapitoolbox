/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sina.weibo.toolbox.util.console;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author X-Spirit
 */
public class CmdReader implements Runnable {

    BufferedReader inputStream;
    String line;
    CmdReaderType type;

    public CmdReader() {
    }

    public CmdReader(BufferedReader inputStream,CmdReaderType type) {
        this.inputStream = inputStream;
        this.type = type;
    }

    public BufferedReader getInputStream() {
        return inputStream;
    }

    public void setInputStream(BufferedReader inputStream) {
        this.inputStream = inputStream;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public CmdReaderType getType() {
        return type;
    }

    public void setType(CmdReaderType type) {
        this.type = type;
    }

    public void run() {
        System.out.println("listener started");
        try {
            while ((line = inputStream.readLine())!=null) {
                //if(line!=null)
                    System.out.println("["+type.name()+"]"+line);
                    System.out.flush();
                //else
                //    return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
