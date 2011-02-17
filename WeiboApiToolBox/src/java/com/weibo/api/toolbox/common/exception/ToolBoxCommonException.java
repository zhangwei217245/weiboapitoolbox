/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.common.exception;

/**
 *
 * @author x-spirit
 */
public class ToolBoxCommonException extends Exception {
    private static final long serialVersionUID = -5077074737185404314L;
    /**
     * Creates a new instance of <code>ToolBoxCommonException</code> without detail message.
     */
    public ToolBoxCommonException() {
    }


    /**
     * Constructs an instance of <code>ToolBoxCommonException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ToolBoxCommonException(String msg) {
        super(msg);
    }

}
