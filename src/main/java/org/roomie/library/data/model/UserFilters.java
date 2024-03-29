package org.roomie.library.data.model;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
// import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.data.annotation.Id;

@DynamoDBTable(tableName = "User_Filters")
public class UserFilters {
	@Id
	private UserFiltersKey userFiltersKey;
	
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

    @DynamoDBHashKey
	public String getEmail() {
		return userFiltersKey != null ? userFiltersKey.getEmail() : null; 
	}

	public void setEmail(String email) {
		if (userFiltersKey == null) {
			userFiltersKey = new UserFiltersKey();
		}
		userFiltersKey.setEmail(email);
	}

	@DynamoDBRangeKey
	public String getId() {
		return userFiltersKey != null ? userFiltersKey.getId() : null; 
	}

	public void setId(String id) {
		if (userFiltersKey == null) {
			userFiltersKey = new UserFiltersKey();
		}
		userFiltersKey.setId(id);
	}

	@DynamoDBAttribute(attributeName = "gender")
    public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@DynamoDBAttribute(attributeName = "age")
    public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@DynamoDBAttribute(attributeName = "nationality")
    public String getNationality() {
		return nationality;
	}

    public void setNationality(String nationality) {
		this.nationality= nationality;
	}

	@DynamoDBAttribute(attributeName = "occupation")
    public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation= occupation;
	}

	@DynamoDBAttribute(attributeName = "minBudget")
    public Integer getMinBudget() {
		return minBudget;
	}

	public void setMaxBudget(Integer maxBudget) {
		this.maxBudget = maxBudget;
	}

	@DynamoDBAttribute(attributeName = "maxBudget")
    public Integer getMaxBudget() {
		return maxBudget;
	}

	public void setMinBudget(Integer minBudget) {
		this.minBudget = minBudget;
	}

	@DynamoDBAttribute(attributeName = "smoking")
    public String getSmoking() {
		return smoking;
	}

	public void setSmoking(String smoking) {
		this.smoking= smoking;
	}

	@DynamoDBAttribute(attributeName = "pets")
    public String getPets() {
		return pets;
	}

	public void setPets(String pets) {
		this.pets= pets;
	}

	@DynamoDBAttribute(attributeName = "food")
    public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food= food;
	}

	@DynamoDBAttribute(attributeName = "riser")
	public String getRiser() {
		return riser;
	}

	public void setRiser(String riser) {
		this.riser = riser;
	}

	@DynamoDBAttribute(attributeName = "sleep")
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
				", id='" + getId() + '\'' + 
				", email='" + getEmail() + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", nationality='" + nationality + '\'' +
                ", occupation='" + occupation + '\'' +
                ", (minBudget)='" + minBudget + '\'' +
				", (maxBudget)='" + maxBudget + '\'' +
                ", smoking='" + smoking + '\'' +
                ", pets='" + pets + '\'' +
                ", foods='" + food + '\'' +
				", riser='" + riser + '\'' +
                ", sleep='" + sleep + '\'' +
				'}';
	}
}
