package com.tdev.discoveryagent.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for repuRequirement complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="repuRequirement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reputationScore" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
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
@XmlType(name = "repuRequirement", propOrder = { "reputationScore", "weight", "nullable" })
public class RepuRequirement {

	protected Double reputationScore;
	protected Double weight;

	private boolean nullable;

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public RepuRequirement(Double weight, Double reputationScore) {
		this.weight = weight;
		this.reputationScore = reputationScore;
	}

	public RepuRequirement() {
	}

	/**
	 * Gets the value of the reputationScore property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getReputationScore() {
		return reputationScore;
	}

	/**
	 * Sets the value of the reputationScore property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setReputationScore(Double value) {
		this.reputationScore = value;
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
