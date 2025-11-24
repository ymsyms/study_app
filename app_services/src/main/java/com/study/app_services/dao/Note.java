package com.study.app_services.dao;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic_id")
    private Long topicId;

    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Note() {}

    public Long getId() { return id; }
    public Long getTopicId() { return topicId; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setTopicId(Long topicId) { this.topicId = topicId; }
    public void setContent(String content) { this.content = content; }
}

