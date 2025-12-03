package com.study.app_services.dto;

public class TopicDto {
	
	private Long id;
	private String title;
	private String description;
	
	public TopicDto() {}

	//For create request
	public TopicDto(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}

	//For response
	public TopicDto(Long id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}

    //getter	
	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}

	//setter	
	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
