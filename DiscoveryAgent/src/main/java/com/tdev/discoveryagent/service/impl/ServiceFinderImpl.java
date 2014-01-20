package com.tdev.discoveryagent.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Endpoint;

import org.apache.commons.lang.math.DoubleRange;
import org.uddi.v3_service.DispositionReportFaultMessage;

import com.tdev.discoveryagent.model.FunctionRequirement;
import com.tdev.discoveryagent.model.MonoType;
import com.tdev.discoveryagent.model.QosRequirement;
import com.tdev.discoveryagent.model.RepuRequirement;
import com.tdev.discoveryagent.model.ServiceEntity;
import com.tdev.discoveryagent.model.ServiceQos;
import com.tdev.discoveryagent.model.ServiceReputaion;
import com.tdev.discoveryagent.service.ServiceFinder;
import com.tdev.discoveryagent.utils.JUDDIHelper;
import com.tdev.discoveryagent.utils.comparator.ServiceEntityDomiQoSComparator;
import com.tdev.discoveryagent.utils.comparator.ServiceEntityOverallScoreComparator;

/**
 * @author tamer
 * 
 */
public class ServiceFinderImpl implements ServiceFinder {

	public static Double LowLimit = 0.1d;

	public static List<ServiceReputaion> REPUTAIONS = null;

	private static JUDDIHelper helper;

	public ServiceFinderImpl() {
		initReputations();
		helper = new JUDDIHelper();
	}

	/* Web services matching, ranking and selection algoritm */
	public List<ServiceEntity> findServices (
			FunctionRequirement functionRequirements,
			QosRequirement qosRequirements, RepuRequirement repuRequirements,
			int maxNumServices) {
		// find services that meet the functional requirements
		List<ServiceEntity> fMatches = fMatch(functionRequirements);
		if(null == fMatches) {
			return null;
		}
		// QoS requirements specified
		List<ServiceEntity> qMatches = null;
		if (null != qosRequirements) {
			// match services with QoS information
			qMatches = qosMatch(fMatches, qosRequirements);
			if(null == qMatches || qMatches.size() < 1) {
				return null;
			}
		} else {
			// select services according the max number of services to be returned
			return selectServices(fMatches, maxNumServices, "random");
		}
		List<ServiceEntity> matches = null;
		if (null != repuRequirements) {// reputation requirements specified
			// rank matches with QoS and reputation information
			matches = reputationRank(qMatches, qosRequirements,	repuRequirements);
			// select services according the max number of services to be returned
			return selectServices(matches, maxNumServices, "byQOS");
		} else {
			// rank matches with QoS information
			matches = qosRank(qMatches, qosRequirements);
			// select services according the max number of services to be returned
			return selectServices(matches, maxNumServices, "byOverall");
		}
	}

	public List<ServiceEntity> fMatch(FunctionRequirement functionRequirement) {
		// search uddi for service according to function requirements
		// return service list
		return helper.findService(functionRequirement.getSearchWord());
	}

	// rank matches with QoS information
	private List<ServiceEntity> qosRank(List<ServiceEntity> services,
			QosRequirement qosReqt) {
		// calculate QoS scores
		services = calculateQoSScore(services, qosReqt);
		// sort the result by QoS score in descending order
		services = sortByQoSScore(services);
		return services;
	}

	public List<ServiceEntity> sortByQoSScore(List<ServiceEntity> services) {
		if (null == services || services.size() < 1) {
			return services;
		}
		Collections.sort(services);
		Collections.reverse(services);
		return services;
	}

	// calculate QoS score (QoS scores: 0~1)
	public List<ServiceEntity> calculateQoSScore(List<ServiceEntity> services,
			QosRequirement qosReqt) {
		// find the highest dominant QoS value
		Double bestQoSValue = findBestDominantQoS(services);
		Double adjustFactor = null;
		if (MonoType.MONO_INCREASING.equals(qosReqt.getType())) {// qosReqt.type.monoIncreasing
			adjustFactor = 1 / bestQoSValue;
			for (ServiceEntity s : services) {// each s in services
				s.setQoSScore(s.getAvailabilityRange().getMaximumDouble() * adjustFactor);
			}
		} else {
			adjustFactor = bestQoSValue;
			for (ServiceEntity s : services) {// each s in services
				s.setQoSScore(adjustFactor / s.getAvailabilityRange().getMaximumDouble());
			}
		}
		return services;
	}

	public Double findBestDominantQoS(List<ServiceEntity> services) {
		Collections.sort(services, new ServiceEntityDomiQoSComparator());
		return services.get(services.size() - 1).getAvailabilityRange().getMaximumDouble();
	}

	// rank services with reputation information
	public List<ServiceEntity> reputationRank(List<ServiceEntity> services,
			QosRequirement qosReqt, RepuRequirement repuReqt) {
		List<ServiceEntity> list = new ArrayList<ServiceEntity>();
		for (ServiceEntity s : services) {// each s in services
			// get reputation mark for each service from reputation manager
			Double r = getReputation(s);
			// remove services whose reputation score is not available or below requirement
			if (r != null && r >= repuReqt.getReputationScore()) {
				s.setReputationScore(r);
				list.add(s);
			} /*else {
				services.remove(s);
			}*/
		} // end for
		// calculate QoS scores
		services = calculateQoSScore(list, qosReqt);
		// calculate adjusted reputation scores
		services = calculateReputationScore(list);
		// calculate overall scores for services
		services = calOverallScore(list, qosReqt.getWeight(),
				repuReqt.getWeight());
		// sort the result by overall score in descending order
		services = sortByOverallScore(list);
		return services;
	}

	public List<ServiceReputaion> getReputations(String serviceId) {
		if (REPUTAIONS == null || REPUTAIONS.size() < 1) {
			return null;
		}
		List<ServiceReputaion> list = new ArrayList<ServiceReputaion>();
		for (ServiceReputaion r : REPUTAIONS) {
			if (serviceId.equalsIgnoreCase(r.getServiceId())) {
				list.add(r);
			}
		}
		return list;
	}

	public Double getReputation(ServiceEntity service) {
		List<ServiceReputaion> reputations = getReputations(service.getId());
		Double reputSum = 0d;
		for (ServiceReputaion x : reputations) {
			reputSum += x.getRating() * service.getTrustFactor();
		}
		Double providerReput = service.getReputationScore()
				* service.getProviderTrustFactor();
		// Calculate reputation
		Double U = reputSum / providerReput;
		return U;
	}

	public void initReputations() {
		File file = new File("src/main/resources/db/service_ratings.csv");
		System.out.println("file name: " + file.getAbsolutePath());

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		REPUTAIONS = new ArrayList<ServiceReputaion>();
		try {
			br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] fields = line.split(cvsSplitBy);
				System.out.println("line: " + line);

				ServiceReputaion reputaion = new ServiceReputaion(fields[0],
						fields[1], Double.valueOf(fields[2]), fields[3]);
				REPUTAIONS.add(reputaion);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Done - reputations size: " + REPUTAIONS.size());
	}

	public List<ServiceEntity> sortByOverallScore(List<ServiceEntity> services) {
		Collections.sort(services, new ServiceEntityOverallScoreComparator());
		return services;
	}

	// calculate adjusted reputation scores (reputation scores: 0~1)
	public List<ServiceEntity> calculateReputationScore(
			List<ServiceEntity> services) {
		// find the highest reputation score
		Double bestRepu = findBestReputation(services);
		for (ServiceEntity s : services) {// each s in services
			s.setReputationScore(s.getReputationScore() / bestRepu);
		}// end for
		return services;
	}

	public Double findBestReputation(List<ServiceEntity> services) {
		Double bestRepu = 0d;
		for (ServiceEntity s : services) {
			if (s.getReputationScore() > bestRepu) {
				bestRepu = s.getReputationScore();
			}
		}
		return bestRepu;
	}

	// calculate overall scores
	public List<ServiceEntity> calOverallScore(List<ServiceEntity> services,
			Double qosWeight, Double repuWeight) {
		for (ServiceEntity s : services) { // each s in services
			s.setOverallScore(s.getQoSScore() * qosWeight
					+ s.getReputationScore() * repuWeight);
		} // end for
		return services;
	}

	// select services according the max number of services to be returned
	public List<ServiceEntity> selectServices(List<ServiceEntity> matches, int maxNumServices, String option) {
		List<ServiceEntity> selection = new ArrayList<ServiceEntity>();
		if (maxNumServices > 1) {
			int i = 0;
			while (i < maxNumServices && i < matches.size()) {
				selection.add(matches.get(i));
				i++;
			}
		} else {
			List<ServiceEntity> candidate = new ArrayList<ServiceEntity>();
			if (option.equals("random")) {
				candidate = matches;
			} else {
				for (ServiceEntity s : matches) { // each s in matches
					if (option.equals("byQoS")) {
						if (s.getQoSScore() >= LowLimit) {
							candidate.add(s);
						}
					} else if (s.getOverallScore() >= LowLimit) {
						candidate.add(s);
					}
				} // end for
			}
			int pickNum = random(0, candidate.size());
			selection.add(candidate.get(pickNum));
		}
		return selection;
	}

	public int random(int Min, int Max) {
		return Min + (int) (Math.random() * ((Max - Min) + 1));
	}

	// find services that match QoS requirements
	public List<ServiceEntity> qosMatch(List<ServiceEntity> services,
			QosRequirement qosReqt) {
		List<ServiceEntity> matches = new ArrayList<ServiceEntity>();
		for (ServiceEntity service : services) {// each s in services
			// get QoS info from UDDI
			// get tModel for given service
			ServiceQos qosAds = getServiceQoS(service);
			// if QoS info available and satisfies QoS requirements
			if (qosAds != null && qosMatchAdvert(qosAds, qosReqt)) {
				matches.add(service);
			} else {
				// Check negotiation
				if(service.getNegotiable()) {
					boolean accepted = service.negotiate(qosReqt);
					if(accepted) {
						matches.add(service);
					}
				}
				// if not do nothing
			}
		}// end for
		return matches;
	}

	private ServiceQos getServiceQoS(ServiceEntity service) {
		// get service key to find binding template with
		String serviceKey = service.getId();
		Map<String, String> tModel = null;
		try {
			// Find tmodel for service
			tModel = helper.findTModelMap(JUDDIHelper.PUBLISHER_AUTHORIZATION_NAME + ":" + service.getName(), serviceKey);
		} catch (DispositionReportFaultMessage e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(null == tModel) {
			return null;
		}
		ServiceQos qos = new ServiceQos();
		// populate ServiceQos with attributes from tmodel
		populateQos(qos, tModel);
		// populate service with qos attributes from tmodel
		populateServiceQosAttributes(service, tModel, qos);
		return qos;
	}
	
	protected void populateQos(ServiceQos qos, Map<String, String> tModel) {
		qos.setAvailability(Double.valueOf(tModel.get("availability")));
		qos.setPrice(Double.valueOf(tModel.get("price")));
		qos.setResponseTime(Double.valueOf(tModel.get("responseTime")));
		qos.setThroughput(Double.valueOf(tModel.get("Throughput")));
		qos.setType(MonoType.valueOf(tModel.get("type")));
	}
	
	protected void populateServiceQosAttributes(ServiceEntity service, Map<String, String> tModel, ServiceQos qos) {
		String responseTimeToRange = tModel.get("responseTimeToRange");
		if(null != responseTimeToRange && !"".equalsIgnoreCase(responseTimeToRange)) {
			service.setResponseTimeRange(new DoubleRange(qos.getResponseTime(), Double.valueOf(responseTimeToRange)));
		} else {
			service.setResponseTimeRange(new DoubleRange(qos.getResponseTime()));
		}
		String throughputToRange = tModel.get("ThroughputToRange");
		if(null != throughputToRange && !"".equalsIgnoreCase(throughputToRange)) {
			service.setThroughputRange(new DoubleRange(qos.getThroughput(), Double.valueOf(throughputToRange)));
		} else {
			service.setThroughputRange(new DoubleRange(qos.getThroughput()));
		}
		String priceToRange = tModel.get("priceToRange");
		if(null != priceToRange && !"".equalsIgnoreCase(priceToRange)) {
			service.setPriceRange(new DoubleRange(qos.getPrice(), Double.valueOf(priceToRange)));
		} else {
			service.setPriceRange(new DoubleRange(qos.getPrice()));
		}
		String availabilityToRange = tModel.get("availabilityToRange");
		if(null != availabilityToRange && !"".equalsIgnoreCase(availabilityToRange)) {
			service.setAvailabilityRange(new DoubleRange(qos.getAvailability(), Double.valueOf(availabilityToRange)));
		} else {
			service.setAvailabilityRange(new DoubleRange(qos.getAvailability()));
		}
		service.setNegotiable(Boolean.valueOf(tModel.get("negotiable")));
		service.setTrustFactor(Double.valueOf(tModel.get("trustFactor")));
		service.setProviderTrustFactor(Double.valueOf(tModel.get("providerTrustFactor")));
	}

	// return true if QoS advertisements match QoS requirements
	public boolean qosMatchAdvert(ServiceQos ads, QosRequirement reqt) {
		return checkQoS(reqt, ads);
	}

	// return true if QoS q1 matches QoS q2
	public boolean checkQoS(QosRequirement q1, ServiceQos q2) {
		if (!q1.getType().equals(q2.getType())) {
			return false;
		}
		// No need for unit conversion we assume unified units
		/*
		 * if (!q1.getUnit().equals(q2.getUnit())) { convertUnit(q1, q2); }
		 */
		boolean result = false;
		switch (q1.getType()) {
		case MONO_INCREASING:
			result = checkMonoIncreasing(q1, q2);
			break;
		case MONO_DECREASING:
			result = checkMonoDecreasing(q1, q2);
			break;
		}
		return result;
	}

	// return true if QoS advertisement q1 satisfies QoS requirement q2
	public boolean checkMonoIncreasing(QosRequirement q1, ServiceQos q2) {
		Boolean result = true;
		result &= MonoType.MONO_INCREASING.equals(q2.getType())
				&& MonoType.MONO_INCREASING.equals(q1.getType());
		if (null != q1.getAvailability()) {
			result &= q1.getAvailability() >= q2.getAvailability();
		}
		if (null != q1.getPrice()) {
			result &= q1.getPrice() >= q2.getPrice();
		}
		if (null != q1.getResponseTime()) {
			result &= q1.getResponseTime() >= q2.getResponseTime();
		}
		if (null != q1.getThroughput()) {
			result &= q1.getThroughput() >= q2.getThroughput();
		}
		return result;
	}

	// return true if QoS advertisement q1 satisfies QoS requirement q2
	public boolean checkMonoDecreasing(QosRequirement q1, ServiceQos q2) {
		Boolean result = true;
		result &= MonoType.MONO_DECREASING.equals(q2.getType())
				&& MonoType.MONO_DECREASING.equals(q1.getType());
		if (null != q1.getAvailability()) {
			result &= q1.getAvailability() <= q2.getAvailability();
		}
		if (null != q1.getPrice()) {
			result &= q1.getPrice() <= q2.getPrice();
		}
		if (null != q1.getResponseTime()) {
			result &= q1.getResponseTime() <= q2.getResponseTime();
		}
		if (null != q1.getThroughput()) {
			result &= q1.getThroughput() <= q2.getThroughput();
		}
		return result;
	}

	public static void main(String[] args) {
		/*ServiceFinderImpl finder = new ServiceFinderImpl();
		
		FunctionRequirement functionRequirements = new FunctionRequirement("DVDRent");
		QosRequirement qosRequirements = new QosRequirement(98.00d, 0.00d, 0.05d, 900d, MonoType.MONO_INCREASING);
		qosRequirements.setWeight(0.5d);
		
		RepuRequirement reput = new RepuRequirement(0.6d, 8d);
		List<ServiceEntity> services = finder.findServices(functionRequirements , qosRequirements , reput , 10);
		if(null == services || services.size() < 1) {
			System.out.println("no service found");
		} else {
			for(ServiceEntity entity : services) {
				System.out.println(entity.toString() + "\n");
			}
		}*/
		
		Endpoint.publish("http://localhost:9999/ws/juddi/agent/find", new JuddiAgentWebserviceImpl());
	}
}
