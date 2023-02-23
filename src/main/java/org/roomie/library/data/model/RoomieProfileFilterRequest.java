package org.roomie.library.data.model;

public class RoomieProfileFilterRequest {
    private String gender;
    private Integer ageCategory;
    private String nationality;
    private String occupation;
    private Integer minBudget;
    private Integer maxBudget;
    private String smoking;
    private String pets;
    private String food;
    private String riser;
	private String sleep;

    public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory(Integer ageCategory) {
		this.ageCategory = ageCategory;
	}

    public Integer getMinAge() {
		if(ageCategory == 1)
			return 0;
		else if(ageCategory == 2)
			return 25;
		else if(ageCategory == 3)
			return 35;
		return 45;
	}

	public Integer getMaxAge() {
		if(ageCategory == 1)
			return 25;
		else if(ageCategory == 2)
			return 35;
		else if(ageCategory == 3)
			return 45;
		return 20000;
	}

    public String getNationality() {
		return nationality;
	}

    public void setNationality(String nationality) {
		this.nationality= nationality;
	}

    public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation= occupation;
	}

    public Integer getMinBudget() {
		return minBudget;
	}

	public void setMinBudget(Integer minBudget) {
		this.minBudget = minBudget;
	}

    public Integer getMaxBudget() {
		return maxBudget;
	}

	public void setMaxBudget(Integer maxBudget) {
		this.maxBudget = maxBudget;
	}

    public String getSmoking() {
		return smoking;
	}

	public void setSmoking(String smoking) {
		this.smoking= smoking;
	}

    public String getPets() {
		return pets;
	}

	public void setPets(String pets) {
		this.pets= pets;
	}

    public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food= food;
	}

	public String getRiser() {
		return riser;
	}

	public void setRiser(String riser) {
		this.riser = riser;
	}

    public String getSleep() {
		return sleep;
	}

	public void setSleep(String sleep) {
		this.sleep = sleep;
	}

    // public int[] getPhotos() {
	// 	return photos;
	// }

	// public void setPhotos(int[] photos) {
	// 	this.photos= photos;
	// }

	@Override
	public String toString() {
		return "UserInfo{" +
                ", gender='" + gender + '\'' +
                ", age='" + ageCategory + '\'' +
                ", nationality='" + nationality + '\'' +
                ", occupation='" + occupation + '\'' +
                ", (minBudget, maxBudget)='" + "(" + minBudget + "," + maxBudget + ")" + '\'' +
                ", smoking='" + smoking + '\'' +
                ", pets='" + pets + '\'' +
                ", foods='" + food + '\'' +
				", riser='" + riser + '\'' +
                ", sleep='" + sleep + '\'' +
				'}';
	}
}
