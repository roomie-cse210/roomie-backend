package org.roomie.library.data.model;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import java.io.Serializable;
import java.util.Objects;

public class UserFiltersKey implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String id;

    public UserFiltersKey() {}

    public UserFiltersKey(String email, String uniqueString) {
        this.email = email;
        this.id = uniqueString;
    }

    @DynamoDBHashKey
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBRangeKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFiltersKey that = (UserFiltersKey) o;
        return email.equals(that.email) && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, id);
    }

    @Override
    public String toString() {
        return "UserFiltersKey{" +
        "email='" + email + '\'' +
        ", id='" + id + '\'' +
        '}';
    }
} 

