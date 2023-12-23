package com.dishcraft.model;

public enum SortRecipesEnum {

	NEW("new"),
	POPULAR("popular") ;
	
	private String title;
	
	private SortRecipesEnum(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
}
