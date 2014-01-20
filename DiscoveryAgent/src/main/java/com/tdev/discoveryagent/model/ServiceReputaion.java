package com.tdev.discoveryagent.model;

public class ServiceReputaion {

	private String serviceId;
	private String customerId;
	private Double rating;
	private String timestamp;

	public ServiceReputaion(
			String serviceId, String customerId, Double rating,	String timestamp) {
		super();
		this.serviceId = serviceId;
		this.customerId = customerId;
		this.rating = rating;
		this.timestamp = timestamp;
	}

	public ServiceReputaion() {
		super();
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
