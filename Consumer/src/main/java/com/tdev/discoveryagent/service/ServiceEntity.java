
package com.tdev.discoveryagent.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for serviceEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="serviceEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="availabilityRange" type="{http://service.discoveryagent.tdev.com/}doubleRange" minOccurs="0"/>
 *         &lt;element name="businessKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="domiQoS" type="{http://service.discoveryagent.tdev.com/}domiQoS" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="negotiable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="overallScore" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="priceRange" type="{http://service.discoveryagent.tdev.com/}doubleRange" minOccurs="0"/>
 *         &lt;element name="providerTrustFactor" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="qoSScore" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="reputationScore" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="responseTimeRange" type="{http://service.discoveryagent.tdev.com/}doubleRange" minOccurs="0"/>
 *         &lt;element name="throughputRange" type="{http://service.discoveryagent.tdev.com/}doubleRange" minOccurs="0"/>
 *         &lt;element name="trustFactor" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceEntity", propOrder = {
    "availabilityRange",
    "businessKey",
    "description",
    "domiQoS",
    "id",
    "name",
    "negotiable",
    "overallScore",
    "priceRange",
    "providerTrustFactor",
    "qoSScore",
    "reputationScore",
    "responseTimeRange",
    "throughputRange",
    "trustFactor"
})
public class ServiceEntity {

    protected DoubleRange availabilityRange;
    protected String businessKey;
    protected String description;
    protected DomiQoS domiQoS;
    protected String id;
    protected String name;
    protected Boolean negotiable;
    protected Double overallScore;
    protected DoubleRange priceRange;
    protected Double providerTrustFactor;
    protected Double qoSScore;
    protected Double reputationScore;
    protected DoubleRange responseTimeRange;
    protected DoubleRange throughputRange;
    protected Double trustFactor;

    /**
     * Gets the value of the availabilityRange property.
     * 
     * @return
     *     possible object is
     *     {@link DoubleRange }
     *     
     */
    public DoubleRange getAvailabilityRange() {
        return availabilityRange;
    }

    /**
     * Sets the value of the availabilityRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link DoubleRange }
     *     
     */
    public void setAvailabilityRange(DoubleRange value) {
        this.availabilityRange = value;
    }

    /**
     * Gets the value of the businessKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessKey() {
        return businessKey;
    }

    /**
     * Sets the value of the businessKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessKey(String value) {
        this.businessKey = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the domiQoS property.
     * 
     * @return
     *     possible object is
     *     {@link DomiQoS }
     *     
     */
    public DomiQoS getDomiQoS() {
        return domiQoS;
    }

    /**
     * Sets the value of the domiQoS property.
     * 
     * @param value
     *     allowed object is
     *     {@link DomiQoS }
     *     
     */
    public void setDomiQoS(DomiQoS value) {
        this.domiQoS = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the negotiable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNegotiable() {
        return negotiable;
    }

    /**
     * Sets the value of the negotiable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNegotiable(Boolean value) {
        this.negotiable = value;
    }

    /**
     * Gets the value of the overallScore property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOverallScore() {
        return overallScore;
    }

    /**
     * Sets the value of the overallScore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOverallScore(Double value) {
        this.overallScore = value;
    }

    /**
     * Gets the value of the priceRange property.
     * 
     * @return
     *     possible object is
     *     {@link DoubleRange }
     *     
     */
    public DoubleRange getPriceRange() {
        return priceRange;
    }

    /**
     * Sets the value of the priceRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link DoubleRange }
     *     
     */
    public void setPriceRange(DoubleRange value) {
        this.priceRange = value;
    }

    /**
     * Gets the value of the providerTrustFactor property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getProviderTrustFactor() {
        return providerTrustFactor;
    }

    /**
     * Sets the value of the providerTrustFactor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setProviderTrustFactor(Double value) {
        this.providerTrustFactor = value;
    }

    /**
     * Gets the value of the qoSScore property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getQoSScore() {
        return qoSScore;
    }

    /**
     * Sets the value of the qoSScore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setQoSScore(Double value) {
        this.qoSScore = value;
    }

    /**
     * Gets the value of the reputationScore property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getReputationScore() {
        return reputationScore;
    }

    /**
     * Sets the value of the reputationScore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setReputationScore(Double value) {
        this.reputationScore = value;
    }

    /**
     * Gets the value of the responseTimeRange property.
     * 
     * @return
     *     possible object is
     *     {@link DoubleRange }
     *     
     */
    public DoubleRange getResponseTimeRange() {
        return responseTimeRange;
    }

    /**
     * Sets the value of the responseTimeRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link DoubleRange }
     *     
     */
    public void setResponseTimeRange(DoubleRange value) {
        this.responseTimeRange = value;
    }

    /**
     * Gets the value of the throughputRange property.
     * 
     * @return
     *     possible object is
     *     {@link DoubleRange }
     *     
     */
    public DoubleRange getThroughputRange() {
        return throughputRange;
    }

    /**
     * Sets the value of the throughputRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link DoubleRange }
     *     
     */
    public void setThroughputRange(DoubleRange value) {
        this.throughputRange = value;
    }

    /**
     * Gets the value of the trustFactor property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTrustFactor() {
        return trustFactor;
    }

    /**
     * Sets the value of the trustFactor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTrustFactor(Double value) {
        this.trustFactor = value;
    }

}
