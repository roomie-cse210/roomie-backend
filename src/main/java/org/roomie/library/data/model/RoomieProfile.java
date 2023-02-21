package org.roomie.library.data.model;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
// import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName = "RoomieProfiles")
public class RoomieProfile {
    private String email;
    private String name;
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
    //private int[] photos;
    private String isPrivate;

    @DynamoDBHashKey
	public String getEmail() {
		return email;
	}

    @DynamoDBAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = name;
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

    public String getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(String isPrivate) {
		this.isPrivate= isPrivate;
	}

	@Override
	public String toString() {
		return "UserInfo{" +
				", email='" + email + '\'' +
                ", name='" + name + '\'' +
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
                ", isPrivate='" + isPrivate + '\'' +
				'}';
	}
}
