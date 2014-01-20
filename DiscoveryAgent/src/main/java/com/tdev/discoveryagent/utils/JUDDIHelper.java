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
package com.tdev.discoveryagent.utils;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.api_v3.Publisher;
import org.apache.juddi.api_v3.SavePublisher;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.apache.juddi.v3_service.JUDDIApiPortType;
import org.uddi.api_v3.AccessPoint;
import org.uddi.api_v3.AuthToken;
import org.uddi.api_v3.BindingDetail;
import org.uddi.api_v3.BindingTemplate;
import org.uddi.api_v3.BindingTemplates;
import org.uddi.api_v3.BusinessDetail;
import org.uddi.api_v3.BusinessEntity;
import org.uddi.api_v3.BusinessList;
import org.uddi.api_v3.BusinessService;
import org.uddi.api_v3.DeleteBusiness;
import org.uddi.api_v3.FindBusiness;
import org.uddi.api_v3.FindQualifiers;
import org.uddi.api_v3.FindService;
import org.uddi.api_v3.GetAuthToken;
import org.uddi.api_v3.GetBindingDetail;
import org.uddi.api_v3.GetServiceDetail;
import org.uddi.api_v3.GetTModelDetail;
import org.uddi.api_v3.KeyedReference;
import org.uddi.api_v3.Name;
import org.uddi.api_v3.SaveBusiness;
import org.uddi.api_v3.SaveService;
import org.uddi.api_v3.ServiceDetail;
import org.uddi.api_v3.ServiceInfo;
import org.uddi.api_v3.ServiceInfos;
import org.uddi.api_v3.ServiceList;
import org.uddi.api_v3.TModelDetail;
import org.uddi.v3_service.DispositionReportFaultMessage;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDIPublicationPortType;
import org.uddi.v3_service.UDDISecurityPortType;

import com.tdev.discoveryagent.model.ServiceEntity;

public class JUDDIHelper {

	private static UDDISecurityPortType security = null;
	private static JUDDIApiPortType juddiApi = null;
	private static UDDIPublicationPortType publish = null;
	private static UDDIInquiryPortType inquiry = null;
	private static String myBusKey = null;
	private static AuthToken myPubAuthToken = null;
	private static AuthToken rootAuthToken;

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
		rootAuthToken = createAuthentication();
		
	}
	
	public List<ServiceEntity> findService(String searchPattern) {
		FindService body = new FindService();
		Name findName = new Name();
		findName.setValue(searchPattern +"%");
		body.getName().add(findName);
		FindQualifiers quilefires = new FindQualifiers();
		quilefires.getFindQualifier().add(org.apache.juddi.query.util.FindQualifiers.APPROXIMATE_MATCH);
		quilefires.getFindQualifier().add(org.apache.juddi.query.util.FindQualifiers.CASE_INSENSITIVE_MATCH);
		body.setFindQualifiers(quilefires);
		ServiceList serviceList;
		try {
			serviceList = inquiry.findService(body);
		} catch (DispositionReportFaultMessage e) {
			e.printStackTrace();
			return null;
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
		ServiceInfos infos = serviceList.getServiceInfos();
		if(null == serviceList || null == infos) {
			return null;
		}
		List<ServiceInfo> serviceInfos = infos.getServiceInfo();
		List<ServiceEntity> entities = new ArrayList<ServiceEntity>();
		for(ServiceInfo info:serviceInfos) {
			entities.add(toServiceEntity(info));
		}
		return entities;
	}
	
	public ServiceEntity toServiceEntity(ServiceInfo info) {
		if(null == info) {
			return null;
		}
		ServiceEntity entity = new ServiceEntity();
		entity.setName(info.getName().get(0).getValue());
		entity.setId(info.getServiceKey());
		entity.setBusinessKey(info.getBusinessKey());
		entity.setReputationScore(Math.random());
		return entity;
	}

	public void publish() {
		try {
			// Create publisher
			createPublisher(rootAuthToken);
			// Our publisher is now saved, so now we want to retrieve its
			// authentication token
			createPublisherAuthentication();
			// Create our business entity
			createBussinesEntity();
			// create service one
			createService("http://localhost:9991/ws/one/rent?wsdl",
					"DVDRentOneService",AccessPointType.WSDL_DEPLOYMENT.toString());
			// create service two
			createService("http://localhost:9992/ws/two/rent?wsdl",
					"DVDRentTwoService",AccessPointType.WSDL_DEPLOYMENT.toString());
			// create service three
			createService("http://localhost:9993/ws/three/rent?wsdl",
					"DVDRentThreeService",AccessPointType.WSDL_DEPLOYMENT.toString());
			// Now you have a publisher saved who in turn published a business and service via the jUDDI API!

		} catch (Exception e) {
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
		GetAuthToken getAuthTokenMyPub = new GetAuthToken();
		getAuthTokenMyPub.setUserID("tdevPublisher");
		getAuthTokenMyPub.setCred("");
		try {
			DeleteBusiness deleteBusiness = new DeleteBusiness();
			deleteBusiness.setAuthInfo(security.getAuthToken(getAuthTokenMyPub).getAuthInfo());

			BusinessList list = findBusiness(businessName);
			if (null == list || null == list.getBusinessInfos()
					|| null == list.getBusinessInfos().getBusinessInfo()
					|| list.getBusinessInfos().getBusinessInfo().size() < 1) {
				return;
			}
			deleteBusiness.getBusinessKey().add(
					list.getBusinessInfos().getBusinessInfo().get(0).getBusinessKey());
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
	
	public Map<String, String> findTModelMap(String key, String serviceKey) throws DispositionReportFaultMessage, RemoteException {
//		BindingDetail details = findBinding(nameValue, serviceKey);
		if(null == myPubAuthToken) {
			createPublisherAuthentication();
		}
		GetTModelDetail tmodelDetails = new GetTModelDetail();
		tmodelDetails.setAuthInfo(myPubAuthToken.getAuthInfo());
		tmodelDetails.getTModelKey().add(key);
		TModelDetail details = inquiry.getTModelDetail(tmodelDetails);
		if (null == details
				|| null == details.getTModel()
				|| null == details.getTModel().get(0)
				|| null == details.getTModel().get(0).getCategoryBag()
				|| null == details.getTModel().get(0).getCategoryBag().getKeyedReference()) {
			return null;
		}
		List<KeyedReference> keyedReferences = details.getTModel().get(0).getCategoryBag().getKeyedReference();
		Map<String, String> attributes = new HashMap<String, String>();
		for(KeyedReference reference : keyedReferences) {
			attributes.put(reference.getKeyName(), reference.getKeyValue());
		}
		return attributes;
	}
	
	public BindingDetail findBinding(String tmodelNameValue, String serviceKey) throws DispositionReportFaultMessage, RemoteException {
		if(null == myPubAuthToken) {
			createPublisherAuthentication();
		}
		GetServiceDetail getServiceDetail=new GetServiceDetail();
	    getServiceDetail.setAuthInfo(myPubAuthToken.getAuthInfo());
	    getServiceDetail.getServiceKey().add(serviceKey);
	    ServiceDetail serviceDetail=inquiry.getServiceDetail(getServiceDetail);
	    BusinessService businessservice=serviceDetail.getBusinessService().get(0);
	    System.out.println("fetched service name:"+businessservice.getName().get(0).getValue());
	    String bindingkey = businessservice.getBindingTemplates().getBindingTemplate().get(0).getBindingKey();
	    System.out.println(bindingkey);

	    GetBindingDetail gbd = new GetBindingDetail();
	    gbd.setAuthInfo(myPubAuthToken.getAuthInfo());
	    gbd.getBindingKey().add(bindingkey);
	    return inquiry.getBindingDetail(gbd);
	}

	/**
	 * Creating a service to save. Only adding the minimum data: the parent
	 * business key retrieved from saving the business above and a single name.
	 * 
	 * @param wsdlUrl
	 * @param serviceName
	 * @param useType
	 * @throws RemoteException
	 * @throws DispositionReportFaultMessage
	 */
	public void createService(String wsdlUrl, String serviceName, String useType)
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

		// Adding the service to the "save" structure, using our publisher's
		// authentication info and saving away.
		SaveService ss = new SaveService();
		ss.getBusinessService().add(myService);
		ss.setAuthInfo(myPubAuthToken.getAuthInfo());
		ServiceDetail sd = publish.saveService(ss);
		String myServKey = sd.getBusinessService().get(0).getServiceKey();
		System.out.println("myService key:  " + myServKey);
	}

	public static void main(String args[]) throws DispositionReportFaultMessage, RemoteException {
		JUDDIHelper sp = new JUDDIHelper();
//		sp.publish();
		List<ServiceEntity> services = sp.findService("DVDRent");
		// get service key to find binding template with
		String serviceKey = services.get(0).getId();
		
		sp.findTModelMap(PUBLISHER_AUTHORIZATION_NAME +":DVDRentOneService", serviceKey);
	}
}
