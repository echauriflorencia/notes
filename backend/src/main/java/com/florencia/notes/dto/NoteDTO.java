package com.florencia.notes.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import java.util.List;

public class NoteDTO {

    private Long id;
    
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;
    
    @NotBlank(message = "Content is required")
    private String content;
    private boolean archived;
    private List<TagDTO> tags;

    public NoteDTO() {
	}

    public NoteDTO(String title, String content, boolean archived) {
    	this.title = title;
    	this.content = content;
    	this.archived = archived;
	}
    
    public NoteDTO(Long id, String title, String content, boolean archived, List<TagDTO> tags) {
    	this.id = id;
    	this.title = title;
    	this.content = content;
    	this.archived = archived;
    	this.tags = tags;
	}


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }
}
