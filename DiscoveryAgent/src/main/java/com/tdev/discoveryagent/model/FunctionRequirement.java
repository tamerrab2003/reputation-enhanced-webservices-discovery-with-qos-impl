package com.tdev.discoveryagent.model;

public class FunctionRequirement {
	
	/**
	 * service name, description
	 */
	private String searchWord;
	
	public FunctionRequirement() {
		
	}
	
	public FunctionRequirement(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	
}