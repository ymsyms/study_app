package com.study.app_services.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.study.app_services.dto.TopicDto;
import com.study.app_services.entity.Topic;
import com.study.app_services.repository.TopicRepository;

@Service
public class TopicService  {
	
	private final TopicRepository repository;
	
	public TopicService(TopicRepository repository) {
		this.repository=repository;
	}

	public TopicDto createTopic(TopicDto dto) {
		Topic entity = new Topic(dto.getTitle(),dto.getDescription());
		Topic created = repository.save(entity);
		return new TopicDto(created.getId(),created.getTitle(),created.getDescription());
	}
	
	public List<TopicDto> getAllTopics() {
		return repository.findAll()
				.stream()
				.map(t -> new TopicDto(t.getId(),t.getTitle(),t.getDescription()))
				.collect(Collectors.toList());
	}
	
	public TopicDto getTopicById(Long id) {
		Topic topic = repository.findById(id).orElseThrow(() -> new RuntimeException("Topic Not found"));
		return new TopicDto(topic.getId(),topic.getTitle(),topic.getDescription());
	}
	
	public TopicDto updateTopic(Long id,TopicDto dto) {
		Topic topic = repository.findById(id).orElseThrow(() -> new RuntimeException("Topic Not Found"));
		topic.setTitle(dto.getTitle());
		topic.setDescription(dto.getDescription());
		Topic updated = repository.save(topic);
		return new TopicDto(updated.getId(),updated.getTitle(),updated.getDescription());
	}
	 public void deleteTopic(Long id) {
		 repository.deleteById(id);
	 }
	
}
	
