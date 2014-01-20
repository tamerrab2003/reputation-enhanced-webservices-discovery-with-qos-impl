package com.tdev.discoveryagent.service.impl;

import java.util.ArrayList;

import javax.jws.WebService;

import org.apache.juddi.v3.annotations.UDDIService;
import org.apache.juddi.v3.annotations.UDDIServiceBinding;

import com.tdev.discoveryagent.model.FunctionRequirement;
import com.tdev.discoveryagent.model.MonoType;
import com.tdev.discoveryagent.model.QosRequirement;
import com.tdev.discoveryagent.model.RepuRequirement;
import com.tdev.discoveryagent.model.ServiceEntity;
import com.tdev.discoveryagent.model.ServiceList;
import com.tdev.discoveryagent.service.JuddiAgentWebservice;
import com.tdev.discoveryagent.service.ServiceFinder;

@UDDIService(businessKey = "uddi:TDev", serviceKey = "uddi:JuddiAgentWebservice", description = "JUDDI Agent Webservice")
@UDDIServiceBinding(bindingKey = "uddi:JuddiAgentWebservice", description = "WSDL endpoint for the JUDDI Agent. This service is used to "
		+ "inhance JUDDI fanctionality", accessPointType = "wsdlDeployment", accessPoint = "http://localhost:9999/ws/juddi/agent/find?wsdl")
@WebService(portName = "JuddiAgentPort", serviceName = "JuddiAgentService", targetNamespace = "http://sortedset.com/wsdl", endpointInterface = "com.tdev.discoveryagent.service.JuddiAgentWebservice")
public class JuddiAgentWebserviceImpl implements JuddiAgentWebservice {

	public ServiceList findServices(
			FunctionRequirement functionRequirements,
			QosRequirement qosRequirements, RepuRequirement repuRequirements, int maxNumServices) {
		
		QosRequirement qosRequirementsClone = qosRequirements;
		if(qosRequirements.isNullable()) {
			qosRequirementsClone = null;
		}
		RepuRequirement repuRequirementsClone = repuRequirements;
		if(repuRequirements.isNullable()) {
			repuRequirementsClone = null;
		}
		
		ServiceFinder finder = new ServiceFinderImpl();
		ArrayList<ServiceEntity> services = (ArrayList<ServiceEntity>) finder
				.findServices(functionRequirements, qosRequirementsClone, repuRequirementsClone, maxNumServices);
		/*if (null == services || services.size() < 1) {
			System.out.println("no service found");
			return null;
		}
		for (ServiceEntity entity : services) {
			System.out.println(entity.toString() + "\n");
		}*/
		return new ServiceList(services);
	}

	public ArrayList<ServiceEntity> find(String pattern, double availability,
			double price, double responseTime, double throughput) {
		ServiceFinder finder = new ServiceFinderImpl();

		FunctionRequirement functionRequirements = new FunctionRequirement(
				pattern);
		QosRequirement qosRequirements = new QosRequirement(availability,
				price, responseTime, throughput, MonoType.MONO_INCREASING);

		RepuRequirement reput = new RepuRequirement(0.6d, 8d);
		ArrayList<ServiceEntity> services = (ArrayList<ServiceEntity>) finder
				.findServices(functionRequirements, qosRequirements, reput, 10);
		if (null == services || services.size() < 1) {
			System.out.println("no service found");
			return null;
		}
		for (ServiceEntity entity : services) {
			System.out.println(entity.toString() + "\n");
		}
		return services;
	}

}
