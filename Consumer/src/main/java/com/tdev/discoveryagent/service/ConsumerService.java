package com.tdev.discoveryagent.service;

import java.net.URL;

import javax.xml.namespace.QName;

import com.sortedset.wsdl.JuddiAgentService;
import com.sortedset.wsdl.JuddiAgentWebservice;
import javax.xml.ws.Service;

public class ConsumerService {

	private JuddiAgentWebservice webservice;

	public ConsumerService() {
		JuddiAgentService proxy = new JuddiAgentService();
		webservice = proxy.getJuddiAgentPort();
	}

	public JuddiAgentWebservice getWebservice() {
		return webservice;
	}

	/*public static void main(String[] args) {
		ConsumerService service = new ConsumerService();
		JuddiAgentWebservice finder = service.getWebservice();

		FunctionRequirement functionRequirements = new FunctionRequirement();
		functionRequirements.setSearchWord("DVDRent");
		QosRequirement qosRequirements = new QosRequirement(98.00d, 0.00d,
				0.05d, 900d, MonoType.MONO_INCREASING);
		qosRequirements.setWeight(0.5d);

		RepuRequirement reput = new RepuRequirement(0.6d, 8d);
		ServiceList serviceList = finder.findServices(functionRequirements,
				null, reput, 10);
		if (null == serviceList || null == serviceList.getList()
				|| serviceList.getList().size() < 1) {
			System.out.println("no service found");
		}
		for (ServiceEntity entity : serviceList.getList()) {
			System.out.println(entity.toString() + "\n");
		}
	}*/

	
	public static void main(String[] args) throws Exception {

		URL url = new URL("http://localhost:9999/ws/juddi/agent/find?wsdl");

		// 1st argument service URI, refer to wsdl document above // 2nd
		// argument
		// is service name, refer to wsdl document above
		QName qname = new QName("http://sortedset.com/wsdl",
				"JuddiAgentService");

		Service service = Service.create(url, qname);

		JuddiAgentWebservice finder = service
				.getPort(JuddiAgentWebservice.class);

		FunctionRequirement functionRequirements = new FunctionRequirement();
		functionRequirements.setSearchWord("DVDRent");
		QosRequirement qosRequirements = new QosRequirement(98.00d, 0.00d,
				0.05d, 900d, MonoType.MONO_INCREASING);
		qosRequirements.setWeight(0.5d);
		
		// Set qosRequirements = null
		qosRequirements.setNullable(true);

		RepuRequirement reput = new RepuRequirement(0.6d, 8d);
		//Set reput = null
		reput.setNullable(true);
		
		ServiceList serviceList = finder.findServices(functionRequirements,	qosRequirements, reput, 10);
		if (null == serviceList || null == serviceList.getList()
				|| serviceList.getList().size() < 1) {
			System.out.println("no service found");
		} else {
			for (ServiceEntity entity : serviceList.getList()) {
				System.out.println(entity.toString() + "\n");
			}
		}
	}
	 
}
