/*
 * Copyright 2001-2010 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.tdev.serviceprovider.webservice;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.api_v3.Publisher;
import org.apache.juddi.api_v3.SavePublisher;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.apache.juddi.v3_service.JUDDIApiPortType;
import org.uddi.api_v3.AccessPoint;
import org.uddi.api_v3.AuthToken;
import org.uddi.api_v3.BindingTemplate;
import org.uddi.api_v3.BindingTemplates;
import org.uddi.api_v3.BusinessDetail;
import org.uddi.api_v3.BusinessEntity;
import org.uddi.api_v3.BusinessInfo;
import org.uddi.api_v3.BusinessList;
import org.uddi.api_v3.BusinessService;
import org.uddi.api_v3.CategoryBag;
import org.uddi.api_v3.DeleteBusiness;
import org.uddi.api_v3.FindBusiness;
import org.uddi.api_v3.GetAuthToken;
import org.uddi.api_v3.KeyedReference;
import org.uddi.api_v3.Name;
import org.uddi.api_v3.SaveBusiness;
import org.uddi.api_v3.SaveService;
import org.uddi.api_v3.SaveTModel;
import org.uddi.api_v3.ServiceDetail;
import org.uddi.api_v3.TModel;
import org.uddi.api_v3.TModelDetail;
import org.uddi.v3_service.DispositionReportFaultMessage;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDIPublicationPortType;
import org.uddi.v3_service.UDDISecurityPortType;

public class JUDDIHelper {

	private static UDDISecurityPortType security = null;
	private static JUDDIApiPortType juddiApi = null;
	private static UDDIPublicationPortType publish = null;
	private static UDDIInquiryPortType inquiry = null;
	private static String myBusKey = null;
	private static AuthToken myPubAuthToken = null;

	public static String PUBLISHER_AUTHORIZATION_NAME = "uddi:tdev.com";
	public static String PUBLISHER_NAME = "TDev Publisher";
	public static String BUSINESS_ENTITY_NAME = PUBLISHER_AUTHORIZATION_NAME + ":ws";
	
	public static final String TMODEL_ONE_NAME_VALE = "DVDRentOneTModel";
	public static final String TMODEL_TWO_NAME_VALE = "DVDRentTwoTModel";
	public static final String TMODEL_THREE_NAME_VALE = "DVDRentThreeTModel";

	public JUDDIHelper() {
		try {
			// create a client and read the config in the archive;
			// you can use your config file name
			UDDIClient uddiClient = new UDDIClient("META-INF/uddi.xml");
			// a UddiClient can be a client to multiple UDDI nodes, so
			// supply the nodeName (defined in your uddi.xml.
			// The transport can be WS, inVM, RMI etc which is defined in the
			// uddi.xml
			Transport transport = uddiClient.getTransport("default");
			// Now you create a reference to the UDDI API
			security = transport.getUDDISecurityService();
			juddiApi = transport.getJUDDIApiService();
			publish = transport.getUDDIPublishService();
			inquiry = transport.getUDDIInquiryService();
			
			// Initialize helper
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() throws DispositionReportFaultMessage, RemoteException {
		// Get root authentication token
		AuthToken rootAuthToken = createAuthentication();
		// Create publisher
		createPublisher(rootAuthToken);
		// Our publisher is now saved, so now we want to retrieve its
		// authentication token
		createPublisherAuthentication();
		// Create our business entity
		createBussinesEntity();
	}

	public void publish() {
		try {
			// initialize tmodel generator
			initTModel();
			// create service one
			createService("http://localhost:9911/ws/one/rent?wsdl",
					"DVDRentOneService1",AccessPointType.WSDL_DEPLOYMENT.toString(), "uddi:tdev.com:50257826-0bad-4096-99b2-94fe7b97bd39");
			createService("http://localhost:9912/ws/one/rent?wsdl",
					"DVDRentOneService2",AccessPointType.WSDL_DEPLOYMENT.toString(), "uddi:tdev.com:50257826-0bad-4096-99b2-94fe7b97bd40");
			createService("http://localhost:9913/ws/one/rent?wsdl",
					"DVDRentOneService3",AccessPointType.WSDL_DEPLOYMENT.toString(), "uddi:tdev.com:50257826-0bad-4096-99b2-94fe7b97bd41");
			createService("http://localhost:9913/ws/one/rent?wsdl",
					"DVDRentOneService4",AccessPointType.WSDL_DEPLOYMENT.toString(), "uddi:tdev.com:50257826-0bad-4096-99b2-94fe7b97bd42");
			addServiceOneQosTModel("DVDRentOneService1");
			addServiceOneQosTModel("DVDRentOneService2");
			addServiceOneQosTModel("DVDRentOneService3");
			addServiceOneQosTModel("DVDRentOneService4");
			// create service two
			createService("http://localhost:9921/ws/two/rent?wsdl",
					"DVDRentTwoService1",AccessPointType.WSDL_DEPLOYMENT.toString(), "uddi:tdev.com:96ecdb3c-0904-4970-9756-6e95644fcf25");
			createService("http://localhost:9922/ws/two/rent?wsdl",
					"DVDRentTwoService2",AccessPointType.WSDL_DEPLOYMENT.toString(), "uddi:tdev.com:96ecdb3c-0904-4970-9756-6e95644fcf26");
			createService("http://localhost:9923/ws/two/rent?wsdl",
					"DVDRentTwoService3",AccessPointType.WSDL_DEPLOYMENT.toString(), "uddi:tdev.com:96ecdb3c-0904-4970-9756-6e95644fcf27");
			createService("http://localhost:9924/ws/two/rent?wsdl",
					"DVDRentTwoService4",AccessPointType.WSDL_DEPLOYMENT.toString(), "uddi:tdev.com:96ecdb3c-0904-4970-9756-6e95644fcf28");
			addServiceTwoQosTModel("DVDRentTwoService1");
			addServiceTwoQosTModel("DVDRentTwoService2");
			addServiceTwoQosTModel("DVDRentTwoService3");
			addServiceTwoQosTModel("DVDRentTwoService4");
			// create service three
			createService("http://localhost:9931/ws/three/rent?wsdl",
					"DVDRentThreeService1",AccessPointType.WSDL_DEPLOYMENT.toString(), "uddi:tdev.com:c1520c53-3e13-471f-8282-400bfbc06c6b");
			createService("http://localhost:9932/ws/three/rent?wsdl",
					"DVDRentThreeService",AccessPointType.WSDL_DEPLOYMENT.toString(), "uddi:tdev.com:c1520c53-3e13-471f-8282-400bfbc06c6c");
			createService("http://localhost:9933/ws/three/rent?wsdl",
					"DVDRentThreeService",AccessPointType.WSDL_DEPLOYMENT.toString(), "uddi:tdev.com:c1520c53-3e13-471f-8282-400bfbc06c6d");
			createService("http://localhost:9934/ws/three/rent?wsdl",
					"DVDRentThreeService",AccessPointType.WSDL_DEPLOYMENT.toString(), "uddi:tdev.com:c1520c53-3e13-471f-8282-400bfbc06c6e");
			addServiceThreeQosTModel("DVDRentThreeService1");
			addServiceThreeQosTModel("DVDRentThreeService2");
			addServiceThreeQosTModel("DVDRentThreeService3");
			addServiceThreeQosTModel("DVDRentThreeService4");
			// Now you have a publisher saved who in turn published a business and service via the jUDDI API!

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initTModel() throws DispositionReportFaultMessage, RemoteException {
		SaveTModel st = new SaveTModel();
		st.setAuthInfo(myPubAuthToken.getAuthInfo());
		TModel tm = new TModel();
		tm.setName(new Name());
		tm.getName().setValue("tdev.com Keymodel generator");
		tm.getName().setLang("en");
		tm.setCategoryBag(new CategoryBag());
		KeyedReference kr = new KeyedReference();
		kr.setTModelKey("uddi:uddi.org:categorization:types");
		kr.setKeyName("uddi-org:keyGenerator");
		kr.setKeyValue("keyGenerator");
		tm.getCategoryBag().getKeyedReference().add(kr);
		tm.setTModelKey("uddi:tdev.com:keygenerator");
		st.getTModel().add(tm);
		
		TModelDetail saveTModel = publish.saveTModel(st);
		System.out.println("Creation of Partition Success! tmodel key: " + saveTModel.getTModel().get(0).getTModelKey());
	}
	
	private void addServiceOneQosTModel(String serviceName) {
		Map<String, String> models1 = new HashMap<String, String>();
		models1.put("responseTime", "0.08");
		models1.put("Throughput", "800");
		models1.put("price", "0.01");
		models1.put("availability", "99.99");
		models1.put("negotiable", "1");
		
		models1.put("responseTimeToRange", "1.2");
		models1.put("ThroughputToRange", "1000");
		models1.put("priceToRange", "1.0");
		models1.put("availabilityToRange", "100");
		models1.put("negotiable", "true");
		
		models1.put("trustFactor", "0.2");
		models1.put("providerTrustFactor", "0.8");
		models1.put("type", "MONO_INCREASING");
		
		try {
			addTModel(PUBLISHER_AUTHORIZATION_NAME +":" + serviceName, TMODEL_ONE_NAME_VALE, models1);
		} catch (DispositionReportFaultMessage e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void addServiceTwoQosTModel(String serviceName) {
		Map<String, String> models1 = new HashMap<String, String>();
		models1.put("responseTime", "0.05");
		models1.put("Throughput", "900");
		models1.put("price", "0.00");
		models1.put("availability", "98.00");
		models1.put("negotiable", "false");
		
		models1.put("trustFactor", "0.7");
		models1.put("providerTrustFactor", "0.6");
		models1.put("type", "MONO_INCREASING");
		try {
			addTModel(PUBLISHER_AUTHORIZATION_NAME +":" + serviceName, TMODEL_TWO_NAME_VALE, models1);
		} catch (DispositionReportFaultMessage e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void addServiceThreeQosTModel(String serviceName) {
		Map<String, String> models1 = new HashMap<String, String>();
		models1.put("responseTime", "0.07");
		models1.put("Throughput", "300");
		models1.put("price", "0.95");
		models1.put("availability", "50.00");
		models1.put("negotiable", "false");
		
		models1.put("trustFactor", "0.9");
		models1.put("providerTrustFactor", "0.4");
		models1.put("type", "MONO_DECREASING");
		try {
			addTModel(PUBLISHER_AUTHORIZATION_NAME +":" + serviceName, TMODEL_THREE_NAME_VALE, models1);
		} catch (DispositionReportFaultMessage e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void createPublisherAuthentication() throws DispositionReportFaultMessage, RemoteException {
		GetAuthToken getAuthTokenMyPub = new GetAuthToken();
		getAuthTokenMyPub.setUserID(PUBLISHER_AUTHORIZATION_NAME);
		getAuthTokenMyPub.setCred("");
		myPubAuthToken = security.getAuthToken(getAuthTokenMyPub);
		System.out.println("myPub AUTHTOKEN = "	+ myPubAuthToken.getAuthInfo());
	}

	/**
	 * Setting up the values to get an authentication token for the 'root' user 
	 * ('root' user has admin privileges and can save other publishers).
	 * 
	 * Making API call that retrieves the authentication token for the 'root' user.
	 * 
	 * @return
	 * @throws RemoteException 
	 * @throws DispositionReportFaultMessage 
	 */
	public AuthToken createAuthentication() throws DispositionReportFaultMessage, RemoteException {
		// Setting up the values to get an authentication token for the
		// 'root' user ('root' user has admin privileges
		// and can save other publishers).
		GetAuthToken getAuthTokenRoot = new GetAuthToken();
		getAuthTokenRoot.setUserID("root");
		getAuthTokenRoot.setCred("root");

		// Making API call that retrieves the authentication token for the
		// 'root' user.
		AuthToken rootAuthToken = security.getAuthToken(getAuthTokenRoot);
		System.out.println("root AUTHTOKEN = " + rootAuthToken.getAuthInfo());
		return rootAuthToken;
	}

	/**
	 * Creating a new publisher that we will use to publish our entities to.
	 * Adding the publisher to the "save" structure, using the 'root' user
	 * authentication info and saving away.
	 * 
	 * @param rootAuthToken
	 * @throws DispositionReportFaultMessage
	 * @throws RemoteException
	 */
	public void createPublisher(AuthToken rootAuthToken)
			throws DispositionReportFaultMessage, RemoteException {
		// Creating a new publisher that we will use to publish our entities to.
		Publisher p = new Publisher();
		p.setAuthorizedName(PUBLISHER_AUTHORIZATION_NAME);
		p.setPublisherName(PUBLISHER_NAME);

		// Adding the publisher to the "save" structure, using the 'root' user
		// authentication info and saving away.
		SavePublisher sp = new SavePublisher();
		sp.getPublisher().add(p);
		sp.setAuthInfo(rootAuthToken.getAuthInfo());
		juddiApi.savePublisher(sp);
	}

	/**
	 * Creating the parent business entity that will contain our service. Adding
	 * the business entity to the "save" structure, using our publisher's
	 * authentication info and saving away.
	 * 
	 * @throws DispositionReportFaultMessage
	 * @throws RemoteException
	 */
	public void createBussinesEntity() throws DispositionReportFaultMessage,
			RemoteException {
		// Creating the parent business entity that will contain our service.
		BusinessEntity myBusEntity = new BusinessEntity();
		Name myBusName = new Name();
		myBusName.setValue(BUSINESS_ENTITY_NAME);
		myBusEntity.getName().add(myBusName);

		// Adding the business entity to the "save" structure, using our
		// publisher's authentication info and saving away.
		SaveBusiness sb = new SaveBusiness();
		sb.getBusinessEntity().add(myBusEntity);
		sb.setAuthInfo(myPubAuthToken.getAuthInfo());
		BusinessDetail bd = publish.saveBusiness(sb);
		myBusKey = bd.getBusinessEntity().get(0).getBusinessKey();
		System.out.println("myBusiness key:  " + myBusKey);
	}

	/**
	 * Delete {@link BusinessEntity} object
	 * 
	 */
	public void deleteBusinessEntity(String businessName) {
		try {
			DeleteBusiness deleteBusiness = new DeleteBusiness();
			if(null == myPubAuthToken) {
				createPublisherAuthentication();
			}
			deleteBusiness.setAuthInfo(myPubAuthToken.getAuthInfo());

			BusinessList list = findBusiness(businessName);
			if (null == list || null == list.getBusinessInfos()
					|| null == list.getBusinessInfos().getBusinessInfo()
					|| list.getBusinessInfos().getBusinessInfo().size() < 1) {
				return;
			}
			for(BusinessInfo info : list.getBusinessInfos().getBusinessInfo()) {
				deleteBusiness.getBusinessKey().add(info.getBusinessKey());
			}
			publish.deleteBusiness(deleteBusiness);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BusinessList findBusiness(String searchPattern) throws Exception {
		FindBusiness fb = new FindBusiness();
		Name findName = new Name();
		findName.setValue(searchPattern);
		fb.getName().add(findName);
		return inquiry.findBusiness(fb);
	}

	/**
	 * Creating a service to save. Only adding the minimum data: the parent
	 * business key retrieved from saving the business above and a single name.
	 * 
	 * @param wsdlUrl
	 * @param serviceName
	 * @param useType
	 * @param serviceKey 
	 * @throws RemoteException
	 * @throws DispositionReportFaultMessage
	 */
	public void createService(String wsdlUrl, String serviceName, String useType, String serviceKey)
			throws DispositionReportFaultMessage, RemoteException {
		BusinessService myService = new BusinessService();
		myService.setBusinessKey(myBusKey);
		Name myServName = new Name();
		myServName.setValue(serviceName);
		myService.getName().add(myServName);

		// Add binding templates, etc...
		BindingTemplate myBindingTemplate = new BindingTemplate();
		AccessPoint accessPoint = new AccessPoint();
		accessPoint.setUseType(useType);
		accessPoint.setValue(wsdlUrl);
		myBindingTemplate.setAccessPoint(accessPoint);
		BindingTemplates myBindingTemplates = new BindingTemplates();
		myBindingTemplates.getBindingTemplate().add(myBindingTemplate);
		myService.setBindingTemplates(myBindingTemplates);
		myService.setServiceKey(serviceKey);

		// Adding the service to the "save" structure, using our publisher's
		// authentication info and saving away.
		SaveService ss = new SaveService();
		ss.getBusinessService().add(myService);
		ss.setAuthInfo(myPubAuthToken.getAuthInfo());
		ServiceDetail sd = publish.saveService(ss);
		String myServKey = sd.getBusinessService().get(0).getServiceKey();
		System.out.println(serviceName + " key:  " + myServKey);
	}
	
	public void addTModel(String key, String nameValue, Map<String, String> models) throws DispositionReportFaultMessage, RemoteException {
		SaveTModel body = new SaveTModel();
		body.setAuthInfo(myPubAuthToken.getAuthInfo());
		TModel t = new TModel();
		t.setTModelKey(key);
		CategoryBag bag = new CategoryBag();
		t.setCategoryBag(bag);
		Name tname = new Name();
		tname.setValue(nameValue);
		t.setName(tname);
		for(Entry<String, String> m : models.entrySet()) {
			KeyedReference kr = new KeyedReference();
			kr.setKeyName(m.getKey());
			kr.setTModelKey(key);
			kr.setKeyValue(m.getValue());
			bag.getKeyedReference().add(kr);
		}
		body.getTModel().add(t);
		TModelDetail saveTModel = publish.saveTModel(body );
		System.out.println("tModels" + saveTModel.getTModel().toString());
	}

	public static void main(String args[]) {
		JUDDIHelper sp = new JUDDIHelper();
		sp.deleteBusinessEntity("uddi:tdev.com:ws");
	}
}
