package com.tdev.discoveryagent.model;

import java.io.Serializable;

import org.apache.commons.lang.math.DoubleRange;

public class ServiceEntity implements Comparable<ServiceEntity>, Serializable {
	
	/**
	 * serialVersionUID = 8357524085945536545L
	 */
	private static final long serialVersionUID = 8357524085945536545L;

	private String id;
	
	private String businessKey;
	
	private String name;
	
	private String description;
	
	private Double reputationScore;
	
	private DomiQoS domiQoS;
	
	private Double qoSScore;
	
	private Double overallScore;
	
	private Double providerTrustFactor;
	
	private Double trustFactor;
	
	// negotiation boolean for all negotiation attributes
	private Boolean negotiable;
	
	//Ranges for negotiatable services
	private DoubleRange priceRange;
	private DoubleRange responseTimeRange;
	private DoubleRange throughputRange;
	private DoubleRange availabilityRange;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setReputationScore(Double r) {
		reputationScore = r;
	}

	public Double getReputationScore() {
		return reputationScore;
	}

	public DomiQoS getDomiQoS() {
		return domiQoS;
	}

	public void setDomiQoS(DomiQoS domiQoS) {
		this.domiQoS = domiQoS;
	}

	public Double getQoSScore() {
		return qoSScore;
	}

	public void setQoSScore(Double qoSScore) {
		this.qoSScore = qoSScore;
	}

	public Double getOverallScore() {
		return overallScore;
	}

	public void setOverallScore(Double overallScore) {
		this.overallScore = overallScore;
	}

	public int compareTo(ServiceEntity o) {
		return getQoSScore().compareTo(o.getQoSScore());
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public Boolean getNegotiable() {
		return negotiable;
	}

	public void setNegotiable(Boolean negotiable) {
		this.negotiable = negotiable;
	}

	public boolean negotiate(QosRequirement qosReqt) {
		if(!getNegotiable()) {
			return false;
		}
		if(!availabilityRange.containsDouble(qosReqt.getAvailability())) {
			return false;
		}
		if(!priceRange.containsDouble(qosReqt.getPrice())) {
			return false;
		}
		if(!responseTimeRange.containsDouble(qosReqt.getResponseTime())) {
			return false;
		}
		if(!throughputRange.containsDouble(qosReqt.getThroughput())) {
			return false;
		}
		return true;
	}

	public DoubleRange getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(DoubleRange priceRange) {
		this.priceRange = priceRange;
	}

	public DoubleRange getResponseTimeRange() {
		return responseTimeRange;
	}

	public void setResponseTimeRange(DoubleRange responseTimeRange) {
		this.responseTimeRange = responseTimeRange;
	}

	public DoubleRange getThroughputRange() {
		return throughputRange;
	}

	public void setThroughputRange(DoubleRange throughputRange) {
		this.throughputRange = throughputRange;
	}

	public DoubleRange getAvailabilityRange() {
		return availabilityRange;
	}

	public void setAvailabilityRange(DoubleRange availabilityRange) {
		this.availabilityRange = availabilityRange;
	}

	public Double getTrustFactor() {
		return trustFactor;
	}

	public void setTrustFactor(Double trustFactor) {
		this.trustFactor = trustFactor;
	}

	public Double getProviderTrustFactor() {
		return providerTrustFactor;
	}

	public void setProviderTrustFactor(Double providerTrustFactor) {
		this.providerTrustFactor = providerTrustFactor;
	}

	@Override
	public String toString() {
		return "ServiceEntity [id=" + id + ", businessKey=" + businessKey
				+ ", name=" + name + ", description=" + description
				+ ", reputationScore=" + reputationScore + ", domiQoS="
				+ domiQoS + ", qoSScore=" + qoSScore + ", overallScore="
				+ overallScore + ", providerTrustFactor=" + providerTrustFactor
				+ ", trustFactor=" + trustFactor + ", negotiable=" + negotiable
				+ ", priceRange=" + priceRange + ", responseTimeRange="
				+ responseTimeRange + ", throughputRange=" + throughputRange
				+ ", availabilityRange=" + availabilityRange + "]";
	}

}
