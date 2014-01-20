package com.tdev.serviceprovider.webservice.impl;

import javax.jws.WebService;

import com.tdev.serviceprovider.webservice.DvdRentalThreeWebservice;

@WebService(
        portName = "DVDRentThreePort",
        serviceName = "DVDRentThreeService",
        targetNamespace = "http://sortedset.com/wsdl",
        endpointInterface = "com.tdev.serviceprovider.webservice.DvdRentalThreeWebservice")
public class DvdRentalThreeWebserviceImpl implements DvdRentalThreeWebservice {

	public boolean rent(String title, String endTime) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		String date = dateFormat.format(endTime);
		System.out.println("DvdRentalThreeWebservice - DVD: " + title + "is rented till " + endTime);
		return true;
	}

}
