//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.22 at 02:02:51 下午 CST 
//


package com.weibo.api.spec.wadl.wadl20090202;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HTTPMethods.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HTTPMethods">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="GET"/>
 *     &lt;enumeration value="POST"/>
 *     &lt;enumeration value="PUT"/>
 *     &lt;enumeration value="HEAD"/>
 *     &lt;enumeration value="DELETE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HTTPMethods")
@XmlEnum
public enum HTTPMethods {

    GET,
    POST,
    PUT,
    HEAD,
    DELETE;

    public String value() {
        return name();
    }

    public static HTTPMethods fromValue(String v) {
        return valueOf(v);
    }

}
