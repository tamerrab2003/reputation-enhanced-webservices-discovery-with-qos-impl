package com.tdev.discoveryagent.service;

import java.util.List;

import com.tdev.discoveryagent.model.FunctionRequirement;
import com.tdev.discoveryagent.model.QosRequirement;
import com.tdev.discoveryagent.model.RepuRequirement;
import com.tdev.discoveryagent.model.ServiceEntity;



public interface ServiceFinder {

	List<ServiceEntity> findServices(FunctionRequirement functionRequirements,
			QosRequirement qosRequirements, RepuRequirement repuRequirements,
			int maxNumServices);

}
