package com.tdev.discoveryagent.model;

public class ServiceQos {
	
	private Double price;
	//in seconds
	private Double responseTime;
	private Double throughput;
	private Double availability;
	private MonoType type;

	public MonoType getType() {
		return type;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Double responseTime) {
		this.responseTime = responseTime;
	}

	public Double getThroughput() {
		return throughput;
	}

	public void setThroughput(Double throughput) {
		this.throughput = throughput;
	}

	public Double getAvailability() {
		return availability;
	}

	public void setAvailability(Double availability) {
		this.availability = availability;
	}

	public void setType(MonoType type) {
		this.type = type;
	}

}
