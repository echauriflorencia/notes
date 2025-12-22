package com.florencia.notes.controller;

import com.florencia.notes.dto.NoteDTO;
import com.florencia.notes.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*") // para React, permitimos cualquier origen
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@Valid @RequestBody NoteDTO noteDTO) {
        return ResponseEntity.ok(noteService.create(noteDTO));
    }

    @GetMapping
    public List<NoteDTO> getAllNotes() {
        return noteService.getAll();
    }

    @GetMapping("/active")
    public List<NoteDTO> getActiveNotes() {
        return noteService.getActive();
    }

    @GetMapping("/archived")
    public List<NoteDTO> getArchivedNotes() {
        return noteService.getArchived();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable Long id) {
    	return ResponseEntity.ok(noteService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable Long id, @RequestBody NoteDTO noteDTO) {
    	return ResponseEntity.ok(noteService.update(id,  noteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity<NoteDTO> archiveNote(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.archive(id));
    }

    @PutMapping("/{id}/unarchive")
    public ResponseEntity<NoteDTO> unarchiveNote(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.unarchive(id));
    }
    
    @PutMapping("/{noteId}/tags/{tagName}")
    public ResponseEntity<NoteDTO> addTag(
    		@PathVariable Long noteId,
    		@PathVariable String tagName) {
    	return ResponseEntity.ok(noteService.addTag(noteId, tagName));
    }
    
    @DeleteMapping("/{noteId}/tags/{tagName}")
    public ResponseEntity<NoteDTO>	removeTag(
    		@PathVariable Long noteId,
			@PathVariable String tagName) {
		return ResponseEntity.ok(noteService.removeTag(noteId, tagName));
	}	
    
    @GetMapping("/by-tag/{tagName}")public List<NoteDTO> getByTag(@PathVariable String tagName) {
    	return noteService.getByTag(tagName);
    }
}
