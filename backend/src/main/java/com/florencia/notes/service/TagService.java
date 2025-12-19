package com.florencia.notes.service;

import com.florencia.notes.model.Tag;
import com.florencia.notes.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
	
	private final TagRepository tagRepository;
	
	public TagService(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}
	
	public Tag create(String name) {
		return tagRepository.findByName(name)
				.orElseGet(() -> tagRepository.save(new Tag() {{
					setName(name);
				}}));
	}
	
	public List<Tag> getAll() {
		return tagRepository.findAll();
	}
}