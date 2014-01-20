package com.tdev.discoveryagent.model;

import java.io.Serializable;

public class RepuRequirement implements Serializable {

	private Double weight;

	private Double reputationScore;

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

	public Double getReputationScore() {
		return reputationScore;
	}

	public void setReputationScore(Double reputationScore) {
		this.reputationScore = reputationScore;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

}
