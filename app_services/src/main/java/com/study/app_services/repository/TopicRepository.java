package com.study.app_services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.study.app_services.entity.Topic;


public interface TopicRepository extends JpaRepository<Topic,Long> {
	
}
