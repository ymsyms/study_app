package com.study.app_services.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="topic_id")
    private Long topicId;

    private String question;
    private String answer;

    public Question() {}

    public Long getId() { return id; }
    public Long getTopicId() { return topicId; }
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }

    public void setTopicId(Long topicId) { this.topicId = topicId; }
    public void setQuestion(String question) { this.question = question; }
    public void setAnswer(String answer) { this.answer = answer; }
}
