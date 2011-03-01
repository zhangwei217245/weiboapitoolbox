package com.weibo.api.toolbox.util;

/**
 *
 * @author x-spirit
 */
public enum CodeMirrorSyntax {
    TXT,CS,CSS,FTL,GROOVY,HTML,JAVA,JS,PHP,PY,REGEX,SQL,WIKI,XML;
    public String lowerName(){
        return this.name().toLowerCase();
    }
}
