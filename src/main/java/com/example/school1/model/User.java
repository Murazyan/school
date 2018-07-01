package com.example.school1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column
    private String password;
    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column
    private int age;
    @Column(name = "pic_url")
    private String picUrl;
    //    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "user_friends",
//            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
//            inverseJoinColumns = { @JoinColumn(name = "friend_id", referencedColumnName = "id") })
//    private List<User> friendsUser;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    protected List<User> friendsUser ;

    @Column
    private boolean note;


}
