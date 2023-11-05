package com.dishcraft.model;

public enum FilterCaloriesEnum {
	UP_TO_200("up_to_200"),
	FROM_200_TO_400("from_200_to_400"),
	FROM_400_TO_600("from_400_to_600"),
	FROM_600_TO_800("from_600_to_800"),
	MORE_THEN_800("more_then_800");
	
	private String title;
	
	private FilterCaloriesEnum(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public boolean filter(Integer calories) {
		if (title == "up_to_200")
			return calories <= 200;
		
		if (title == "from_200_to_400")
			return calories <= 400 && calories > 200;
		
		if (title == "from_400_to_600")
			return calories <= 600 && calories > 400;
		
		if (title == "from_600_to_800")
			return calories <= 800 && calories > 600;
		
		return true;
	}
}
