package com.tdev.serviceprovider.webservice.impl;

import javax.jws.WebService;

import com.tdev.serviceprovider.webservice.DvdRentalTwoWebservice;

@WebService(
        portName = "DVDRentTwoPort",
        serviceName = "DVDRentTwoService",
        targetNamespace = "http://sortedset.com/wsdl",
        endpointInterface = "com.tdev.serviceprovider.webservice.DvdRentalTwoWebservice")
public class DvdRentalTwoWebserviceImpl implements DvdRentalTwoWebservice {

	public boolean rent(String title, String endTime) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		String date = dateFormat.format(endTime);
		System.out.println("DvdRentalTwoWebservice - DVD: " + title + "is rented till " + endTime);
		return true;
	}

}
