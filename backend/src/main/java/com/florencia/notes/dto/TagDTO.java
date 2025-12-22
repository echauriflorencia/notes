package com.florencia.notes.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TagDTO {
	
	private Long id;
	
	@NotBlank(message = "Name is required")
	@Size(max = 30, message = "Name must be at most 30 characters")
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}