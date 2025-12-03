package com.study.app_services.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.app_services.dto.TopicDto;
import com.study.app_services.service.TopicService;

@RestController
@RequestMapping("/topics")
public class TopicController {
	
	private final TopicService service;
	
	public TopicController(TopicService service) {
		this.service = service;
	}

	@PostMapping
	public TopicDto createTopic(@RequestBody TopicDto dto) {
		return service.createTopic(dto);
	}
	
	@GetMapping
	public List<TopicDto> getAllTopics()
	{
		return service.getAllTopics();
	}
	
	@GetMapping("/{id}")
	public TopicDto getTopicById(@PathVariable Long id) {
		return service.getTopicById(id);
	}
	
	@PutMapping("{id}")
	public TopicDto updateTopic(@PathVariable Long id, @RequestBody TopicDto dto) {
		return service.updateTopic(id, dto);
	}
	
	@DeleteMapping("/{id}")
	public void deleteTopic(@PathVariable Long id) {
		service.deleteTopic(id);
	}
}
