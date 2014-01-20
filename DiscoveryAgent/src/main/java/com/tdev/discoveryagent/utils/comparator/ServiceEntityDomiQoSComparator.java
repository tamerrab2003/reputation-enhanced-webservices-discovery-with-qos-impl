package com.tdev.discoveryagent.utils.comparator;

import java.util.Comparator;

import com.tdev.discoveryagent.model.ServiceEntity;

public class ServiceEntityDomiQoSComparator implements Comparator<ServiceEntity>{

	public int compare(ServiceEntity arg0, ServiceEntity arg1) {
		return Double.valueOf(arg0.getAvailabilityRange().getMaximumDouble())
				.compareTo(arg1.getAvailabilityRange().getMaximumDouble());
	}

}
