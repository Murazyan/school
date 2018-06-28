package com.example.school1.reposotory;

import com.example.school1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findOneByEmail(String email);
    List<User> findAllByNameOrSurname(String name, String surname);
    List<User> findAllByName(String name);
    List<User> findAllBySurname(String surname);


}
