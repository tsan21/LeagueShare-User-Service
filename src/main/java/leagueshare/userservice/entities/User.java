package leagueshare.userservice.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long user_id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;

    public User() {
    }

    public User(String userName, String email, String firstName, String lastName) {
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
