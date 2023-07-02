package com.shopme.common.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer userId;

    @Column(name="email", unique = true)
    private String email;

//    @Size(min = 6, max = 12, message = "Password must between 6 and 12!")
    @Column(name="password")
    private String password;

    @NotBlank(message = "Firstname can not be blank!")
    @Column(name="first_name")
    private String firstName;

    @NotBlank(message = "Lastname can not be blank!")
    @Column(name="last_name")
    private String lastName;

    private String photos;
    private Boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleApp> roles = new HashSet<>();

    @Transient
    public String getPhotoImagePath(){
        if(this.userId == null || this.photos == null) return "/images/default-user.jpg";
        return "/user-photos/" + userId + "/" + this.photos;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
