package com.example.school1.reposotory;

import com.example.school1.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Integer> {



}
