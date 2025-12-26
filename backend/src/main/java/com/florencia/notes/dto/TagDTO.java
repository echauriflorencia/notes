package com.florencia.notes.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tag data transfer object")
public class TagDTO {
	
	@Schema(example = "1", description = "Unique identifier of the tag")
	private Long id;
	
	@Schema(example = "Work", description = "Name of the tag")
	@NotBlank(message = "Name is required")
	@Size(max = 30, message = "Name must be at most 30 characters")
	private String name;
	
	public TagDTO() {
	}
	
	public TagDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
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