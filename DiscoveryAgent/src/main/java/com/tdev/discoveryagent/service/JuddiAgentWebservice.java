package com.tdev.discoveryagent.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.tdev.discoveryagent.model.FunctionRequirement;
import com.tdev.discoveryagent.model.QosRequirement;
import com.tdev.discoveryagent.model.RepuRequirement;
import com.tdev.discoveryagent.model.ServiceList;

//Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)
@XmlType(propOrder = {
		"qosRequirements",
		"repuRequirements"
})
public interface JuddiAgentWebservice {
	
	@WebMethod
	public ServiceList findServices(
			FunctionRequirement functionRequirements,
			QosRequirement qosRequirements, RepuRequirement repuRequirements, int maxNumServices);
	
}
