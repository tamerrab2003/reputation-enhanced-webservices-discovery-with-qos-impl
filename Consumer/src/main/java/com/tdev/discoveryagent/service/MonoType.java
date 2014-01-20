
package com.tdev.discoveryagent.service;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="monoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MONO_INCREASING"/>
 *     &lt;enumeration value="MONO_DECREASING"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "monoType")
@XmlEnum
public enum MonoType {

    MONO_INCREASING,
    MONO_DECREASING;

    public String value() {
        return name();
    }

    public static MonoType fromValue(String v) {
        return valueOf(v);
    }

}
