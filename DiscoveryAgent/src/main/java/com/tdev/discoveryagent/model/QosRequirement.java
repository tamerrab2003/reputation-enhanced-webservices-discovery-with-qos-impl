package com.tdev.discoveryagent.model;

import java.io.Serializable;

public class QosRequirement implements Serializable{

	private MonoType type;
	private DomiQoS domiQoS;
	private Double weight;
	
	private Double price;
	//in seconds
	private Double responseTime;
	private Double throughput;
	private Double availability;
	private Double latency;
	
	private boolean nullable;
	
	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public QosRequirement(double availability, double price, double responseTime, double throughput, MonoType type) {
		this.availability = availability;
		this.price = price;
		this.responseTime = responseTime;
		this.throughput = throughput;
		this.type = type;
	}
	
	public QosRequirement() {
	}

	public MonoType getType() {
		return type;
	}

	public void setType(MonoType type) {
		this.type = type;
	}

	public DomiQoS getDomiQoS() {
		return domiQoS;
	}

	public void setDomiQoS(DomiQoS domiQoS) {
		this.domiQoS = domiQoS;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
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

	public Double getLatency() {
		return latency;
	}

	public void setLatency(Double latency) {
		this.latency = latency;
	}

}
