package com.tdev.serviceprovider.webservice;

import javax.xml.ws.Endpoint;

import com.tdev.serviceprovider.webservice.impl.DvdRentalOneWebserviceImpl;
import com.tdev.serviceprovider.webservice.impl.DvdRentalThreeWebserviceImpl;
import com.tdev.serviceprovider.webservice.impl.DvdRentalTwoWebserviceImpl;

/**
 * Hello world!
 * 
 */
public class App {
	
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:9911/ws/one/rent", new DvdRentalOneWebserviceImpl());
		Endpoint.publish("http://localhost:9912/ws/one/rent", new DvdRentalOneWebserviceImpl());
		Endpoint.publish("http://localhost:9913/ws/one/rent", new DvdRentalOneWebserviceImpl());
		Endpoint.publish("http://localhost:9914/ws/one/rent", new DvdRentalOneWebserviceImpl());
		
		Endpoint.publish("http://localhost:9921/ws/two/rent", new DvdRentalTwoWebserviceImpl());
		Endpoint.publish("http://localhost:9922/ws/two/rent", new DvdRentalTwoWebserviceImpl());
		Endpoint.publish("http://localhost:9923/ws/two/rent", new DvdRentalTwoWebserviceImpl());
		Endpoint.publish("http://localhost:9924/ws/two/rent", new DvdRentalTwoWebserviceImpl());
		
		Endpoint.publish("http://localhost:9931/ws/three/rent", new DvdRentalThreeWebserviceImpl());
		Endpoint.publish("http://localhost:9932/ws/three/rent", new DvdRentalThreeWebserviceImpl());
		Endpoint.publish("http://localhost:9933/ws/three/rent", new DvdRentalThreeWebserviceImpl());
		Endpoint.publish("http://localhost:9934/ws/three/rent", new DvdRentalThreeWebserviceImpl());
		
		JUDDIHelper helper = new JUDDIHelper();
		// Delete if exists
//		helper.deleteBusinessEntity("TDev");
		// Publish services
		helper.publish();
	}
	
	public App() { }
	
}
