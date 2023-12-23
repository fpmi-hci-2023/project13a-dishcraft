package com.dishcraft.model;

public enum FilterTimeEnum {
	
	UP_TO_15("up_to_15"),
	UP_TO_30("up_to_30"),
	UP_TO_45("up_to_45"),
	UP_TO_HOUR("up_to_hour"),
	MORE_THAN_HOUR("more_than_hour");
	
	private String title;
	
	private FilterTimeEnum(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public boolean filter(Recipe recipe) {
		if (title == "up_to_15")
			return recipe.getCookingTimeMinutes() <= 15;
		
		if (title == "up_to_30")
			return recipe.getCookingTimeMinutes() <= 30;
		
		if (title == "up_to_45")
			return recipe.getCookingTimeMinutes() <= 45;
		
		if (title == "up_to_hour")
			return recipe.getCookingTimeMinutes() <= 60;
		
		return true;
	}
}
