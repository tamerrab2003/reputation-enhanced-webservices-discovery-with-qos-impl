package com.tdev.discoveryagent.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for qosRequirement complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="qosRequirement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="availability" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="domiQoS" type="{http://service.discoveryagent.tdev.com/}domiQoS" minOccurs="0"/>
 *         &lt;element name="latency" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="responseTime" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="throughput" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="type" type="{http://service.discoveryagent.tdev.com/}monoType" minOccurs="0"/>
 *         &lt;element name="weight" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "qosRequirement", propOrder = { "availability", "domiQoS",
		"latency", "price", "responseTime", "throughput", "type", "weight", "nullable" })
public class QosRequirement {

	protected Double availability;
	protected DomiQoS domiQoS;
	protected Double latency;
	protected Double price;
	protected Double responseTime;
	protected Double throughput;
	protected MonoType type;
	protected Double weight;

	private boolean nullable;

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public QosRequirement() {
	}

	public QosRequirement(double availability, double price,
			double responseTime, double throughput, MonoType type) {
		this.availability = availability;
		this.price = price;
		this.responseTime = responseTime;
		this.throughput = throughput;
		this.type = type;
	}

	/**
	 * Gets the value of the availability property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getAvailability() {
		return availability;
	}

	/**
	 * Sets the value of the availability property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setAvailability(Double value) {
		this.availability = value;
	}

	/**
	 * Gets the value of the domiQoS property.
	 * 
	 * @return possible object is {@link DomiQoS }
	 * 
	 */
	public DomiQoS getDomiQoS() {
		return domiQoS;
	}

	/**
	 * Sets the value of the domiQoS property.
	 * 
	 * @param value
	 *            allowed object is {@link DomiQoS }
	 * 
	 */
	public void setDomiQoS(DomiQoS value) {
		this.domiQoS = value;
	}

	/**
	 * Gets the value of the latency property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getLatency() {
		return latency;
	}

	/**
	 * Sets the value of the latency property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setLatency(Double value) {
		this.latency = value;
	}

	/**
	 * Gets the value of the price property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets the value of the price property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setPrice(Double value) {
		this.price = value;
	}

	/**
	 * Gets the value of the responseTime property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getResponseTime() {
		return responseTime;
	}

	/**
	 * Sets the value of the responseTime property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setResponseTime(Double value) {
		this.responseTime = value;
	}

	/**
	 * Gets the value of the throughput property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getThroughput() {
		return throughput;
	}

	/**
	 * Sets the value of the throughput property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setThroughput(Double value) {
		this.throughput = value;
	}

	/**
	 * Gets the value of the type property.
	 * 
	 * @return possible object is {@link MonoType }
	 * 
	 */
	public MonoType getType() {
		return type;
	}

	/**
	 * Sets the value of the type property.
	 * 
	 * @param value
	 *            allowed object is {@link MonoType }
	 * 
	 */
	public void setType(MonoType value) {
		this.type = value;
	}

	/**
	 * Gets the value of the weight property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * Sets the value of the weight property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setWeight(Double value) {
		this.weight = value;
	}

}
