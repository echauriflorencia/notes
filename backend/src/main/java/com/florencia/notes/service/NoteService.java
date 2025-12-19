package com.florencia.notes.service;

import com.florencia.notes.model.Note;
import com.florencia.notes.model.Tag;
import com.florencia.notes.repository.NoteRepository;
import com.florencia.notes.repository.TagRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final TagRepository tagRepository;
    
    public NoteService(NoteRepository noteRepository, TagRepository tagRepository) {
        this.noteRepository = noteRepository;
        this.tagRepository = tagRepository;
    }

    public Note create(Note note) {
        return noteRepository.save(note);
    }

    public List<Note> getAll() {
        return noteRepository.findAll();
    }

    public List<Note> getActive() {
        return noteRepository.findByArchivedFalse();
    }

    public List<Note> getArchived() {
        return noteRepository.findByArchivedTrue();
    }

    public Optional<Note> getById(Long id) {
        return noteRepository.findById(id);
    }

    public Note update(Note note) {
        return noteRepository.save(note);
    }

    public void delete(Long id) {
        noteRepository.deleteById(id);
    }

    public Note archive(Long id) {
        Note note = noteRepository.findById(id).orElseThrow();
        note.setArchived(true);
        return noteRepository.save(note);
    }

    public Note unarchive(Long id) {
        Note note = noteRepository.findById(id).orElseThrow();
        note.setArchived(false);
        return noteRepository.save(note);
    }
    
    @Transactional
    public Note addTag(Long noteId, String tagName) {
    	Note note = noteRepository.findById(noteId)
    			.orElseThrow(() -> new RuntimeException("Note not found"));
    	
    	Tag tag = tagRepository.findByName(tagName)
    			.orElseGet(() -> {
					Tag newTag = new Tag();
					newTag.setName(tagName);
					return tagRepository.save(newTag);
				});
    	
    	note.getTags().add(tag);
    	tag.getNotes().add(note);
    	
    	return noteRepository.save(note);
	}
    
    @Transactional
    public Note removeTag(Long noteId, String tagName) {
    	Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new RuntimeException("Note not found"));
    	
    	Tag tag = tagRepository.findByName(tagName)
    			.orElseThrow(() -> new RuntimeException("Tag not found"));
    	
    	note.getTags().remove(tag);
    	tag.getNotes().remove(note);
    	
    	return noteRepository.save(note);
    }
    
    public List<Note> getByTag(String tagName) {
    	return noteRepository.findByTagName(tagName);
    }
     
}
