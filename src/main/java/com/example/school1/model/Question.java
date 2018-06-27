package com.example.school1.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private  int id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Date date;
    @ManyToOne
    private User user;
    @ManyToOne
    private Lesson lesson;
    @Column(name = "pic_url")
    private String picUrl;
    @ManyToOne
    private Comment comment;
}
