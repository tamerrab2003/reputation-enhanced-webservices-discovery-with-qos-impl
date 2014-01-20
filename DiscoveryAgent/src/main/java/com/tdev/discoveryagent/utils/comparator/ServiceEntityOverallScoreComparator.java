package com.tdev.discoveryagent.utils.comparator;

import java.util.Comparator;

import com.tdev.discoveryagent.model.ServiceEntity;

public class ServiceEntityOverallScoreComparator implements Comparator<ServiceEntity>{

	public int compare(ServiceEntity arg0, ServiceEntity arg1) {
		return arg0.getOverallScore().compareTo(arg1.getOverallScore());
	}

}
