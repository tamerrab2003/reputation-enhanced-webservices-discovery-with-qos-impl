package com.tdev.discoveryagent.model;

import java.util.ArrayList;
import java.util.List;

public class ServiceList {
	private List<ServiceEntity> list;

	public ServiceList(List<ServiceEntity> list) {
		this.list = new ArrayList<ServiceEntity>(list);
	}
	
	public List<ServiceEntity> getList() {
		return list;
	}

	public void setList(List<ServiceEntity> list) {
		this.list = list;
	}
}
