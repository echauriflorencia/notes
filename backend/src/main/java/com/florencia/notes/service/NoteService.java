package com.florencia.notes.service;

import com.florencia.notes.dto.NoteDTO;
import com.florencia.notes.exception.ResourceNotFoundException;
import com.florencia.notes.mapper.NoteMapper;
import com.florencia.notes.model.Note;
import com.florencia.notes.model.Tag;
import com.florencia.notes.repository.NoteRepository;
import com.florencia.notes.repository.TagRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final TagRepository tagRepository;
    
    public NoteService(NoteRepository noteRepository, TagRepository tagRepository) {
        this.noteRepository = noteRepository;
        this.tagRepository = tagRepository;
    }

    public NoteDTO create(NoteDTO noteDTO) {
    	Note note = NoteMapper.toEntity(noteDTO);
        return NoteMapper.toDTO(noteRepository.save(note));
    }

    public List<NoteDTO> getAll() {
        return noteRepository.findAll().stream()
        		.map(NoteMapper::toDTO)
        		.collect(Collectors.toList()); 
    }

    public List<NoteDTO> getActive() {
        return noteRepository.findByArchivedFalse().stream()
                .map(NoteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<NoteDTO> getArchived() {
        return noteRepository.findByArchivedTrue().stream()
				.map(NoteMapper::toDTO)
				.collect(Collectors.toList());
    }

    public NoteDTO getById(Long id) {
    	return noteRepository.findById(id)
                .map(NoteMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));
    }

    public NoteDTO update(Long id, NoteDTO noteDTO) {
    	Note note = noteRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("Note not found"));
    			
    	note.setTitle(noteDTO.getTitle());
    	note.setContent(noteDTO.getContent());
        
    	return NoteMapper.toDTO(noteRepository.save(note));
    }

    public void delete(Long id) {
        noteRepository.deleteById(id);
    }

    public NoteDTO archive(Long id) {
        Note note = noteRepository.findById(id).orElseThrow();
        note.setArchived(true);
        return NoteMapper.toDTO(noteRepository.save(note));
    }

    public NoteDTO unarchive(Long id) {
        Note note = noteRepository.findById(id).orElseThrow();
        note.setArchived(false);
        return NoteMapper.toDTO(noteRepository.save(note));
    }
    
    @Transactional
    public NoteDTO addTag(Long noteId, String tagName) {
    	Note note = noteRepository.findById(noteId)
    			.orElseThrow(() -> new ResourceNotFoundException("Note not found"));
    	
    	Tag tag = tagRepository.findByName(tagName)
    			.orElseGet(() -> {
					Tag newTag = new Tag();
					newTag.setName(tagName);
					return tagRepository.save(newTag);
				});
    	
    	note.getTags().add(tag);
    	tag.getNotes().add(note);
    	
    	Note saved = noteRepository.save(note);
    	
    	return NoteMapper.toDTO(saved);
	}
    
    @Transactional
    public NoteDTO removeTag(Long noteId, String tagName) {
    	Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note not found"));
    	
    	Tag tag = tagRepository.findByName(tagName)
    			.orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
    	
    	note.getTags().remove(tag);
    	tag.getNotes().remove(note);
    	
    	Note saved = noteRepository.save(note);
    	
    	return NoteMapper.toDTO(saved);
    }
    
    public List<NoteDTO> getByTag(String tagName) {
    	return noteRepository.findByTagName(tagName).stream()
				.map(NoteMapper::toDTO)
				.collect(Collectors.toList());
    }
     
}
