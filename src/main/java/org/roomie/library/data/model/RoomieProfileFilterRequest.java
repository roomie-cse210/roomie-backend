package org.roomie.library.data.model;

public class RoomieProfileFilterRequest {
    private String gender;
    private Integer age;
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

    public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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
                ", age='" + age + '\'' +
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
