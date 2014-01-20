package com.tdev.serviceprovider.webservice;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

//Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)
public interface DvdRentalThreeWebservice {
	
	public boolean rent(String title, String endTime);
	
}
