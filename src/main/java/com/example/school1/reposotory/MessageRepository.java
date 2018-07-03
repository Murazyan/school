package com.example.school1.reposotory;

import com.example.school1.model.Message;
import com.example.school1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {
List<Message> findAllByFromUserAndToUser(User a, User b);
}
