package org.roomie.library.data.model;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
// import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName = "Roomie_Record")
public class RoomieProfile {
    private String email;
    private String name;
    private String gender;
    private Integer age;
    private String nationality;
    private String occupation;
    private Integer approxBudget;
    private String smoking;
    private String pets;
    private String food;
    private String riser;
	private String sleep;
    //private int[] photos;
    private String isPrivate;
	private String description;

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

	@DynamoDBAttribute(attributeName = "approxBudget")
    public Integer getApproxBudget() {
		return approxBudget;
	}

	public void setApproxBudget(Integer approxBudget) {
		this.approxBudget = approxBudget;
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

	@DynamoDBAttribute(attributeName = "isPrivate")
    public String getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(String isPrivate) {
		this.isPrivate= isPrivate;
	}

	@DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description= description;
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
                ", (approxBudget)='" + approxBudget + '\'' +
                ", smoking='" + smoking + '\'' +
                ", pets='" + pets + '\'' +
                ", foods='" + food + '\'' +
				", riser='" + riser + '\'' +
                ", sleep='" + sleep + '\'' +
                ", isPrivate='" + isPrivate + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
