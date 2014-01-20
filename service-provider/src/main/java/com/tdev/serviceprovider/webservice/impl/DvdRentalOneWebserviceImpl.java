package com.tdev.serviceprovider.webservice.impl;

import javax.jws.WebService;

import org.apache.juddi.v3.annotations.UDDIService;
import org.apache.juddi.v3.annotations.UDDIServiceBinding;

import com.tdev.serviceprovider.webservice.DvdRentalOneWebservice;

@UDDIService(
		businessKey="uddi:TDev",
		serviceKey="uddi:DvdRentalOneService", 
		description = "Dvd Rental Webservice")
@UDDIServiceBinding(
		bindingKey="uddi:DvdRentalOneService",
	    description="WSDL endpoint for the DvdRentalOneService. This service is used for "
				  + "simulating DVD rental service",
	    accessPointType="wsdlDeployment",
	    accessPoint="http://localhost:9991/ws/one/rent?wsdl")
@WebService(
        portName = "DVDRentOnePort",
        serviceName = "DVDRentOneService",
        targetNamespace = "http://sortedset.com/wsdl",
        endpointInterface = "com.tdev.serviceprovider.webservice.DvdRentalOneWebservice")
public class DvdRentalOneWebserviceImpl implements DvdRentalOneWebservice {

	public boolean rent(String title, String endTime) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		String date = dateFormat.format(endTime);
		System.out.println("DvdRentalOneWebservice - DVD: " + title + "is rented till " + endTime);
		return true;
	}

}
