
package com.tdev.discoveryagent.service;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tdev.discoveryagent.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tdev.discoveryagent.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RepuRequirement }
     * 
     */
    public RepuRequirement createRepuRequirement() {
        return new RepuRequirement();
    }

    /**
     * Create an instance of {@link QosRequirement }
     * 
     */
    public QosRequirement createQosRequirement() {
        return new QosRequirement();
    }

    /**
     * Create an instance of {@link FunctionRequirement }
     * 
     */
    public FunctionRequirement createFunctionRequirement() {
        return new FunctionRequirement();
    }

    /**
     * Create an instance of {@link ServiceEntity }
     * 
     */
    public ServiceEntity createServiceEntity() {
        return new ServiceEntity();
    }

    /**
     * Create an instance of {@link ServiceList }
     * 
     */
    public ServiceList createServiceList() {
        return new ServiceList();
    }

    /**
     * Create an instance of {@link DomiQoS }
     * 
     */
    public DomiQoS createDomiQoS() {
        return new DomiQoS();
    }

    /**
     * Create an instance of {@link DoubleRange }
     * 
     */
    public DoubleRange createDoubleRange() {
        return new DoubleRange();
    }

}
