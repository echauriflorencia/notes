package com.florencia.notes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.florencia.notes.dto.NoteDTO;
import com.florencia.notes.service.NoteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*") // para React, permitimos cualquier origen
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Operation(summary = "Create a new note")
    @ApiResponses({
		@ApiResponse(responseCode = "200", description = "Note created successfully"),
		@ApiResponse(responseCode = "400", description = "Validation error")
	})
    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@Valid @RequestBody NoteDTO noteDTO) {
        return ResponseEntity.ok(noteService.create(noteDTO));
    }

    @Operation(summary = "Get all notes")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "List of notes")
    })
    @GetMapping
    public List<NoteDTO> getAllNotes() {
        return noteService.getAll();
    }

    @Operation(summary = "Get all active notes")
    @ApiResponses({
		@ApiResponse(responseCode = "200", description = "List of active notes")
	})
    @GetMapping("/active")
    public List<NoteDTO> getActiveNotes() {
        return noteService.getActive();
    }

    @Operation(summary = "Get all archived notes")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "List of archived notes")
    })
    @GetMapping("/archived")
    public List<NoteDTO> getArchivedNotes() {
        return noteService.getArchived();
    }

    @Operation(summary = "Get note by ID")
    @ApiResponses({
		@ApiResponse(responseCode = "200", description = "Note found"),
		@ApiResponse(responseCode = "404", description = "Note not found")
	})
    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable Long id) {
    	return ResponseEntity.ok(noteService.getById(id));
    }

    @Operation(summary = "Update a note")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "Note updated"),
    	@ApiResponse(responseCode = "404", description = "Note not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable Long id, @RequestBody NoteDTO noteDTO) {
    	return ResponseEntity.ok(noteService.update(id,  noteDTO));
    }

    @Operation(summary = "Delete a note")
    @ApiResponses({
		@ApiResponse(responseCode = "204", description = "Note deleted"),
		@ApiResponse(responseCode = "404", description = "Note not found")
	})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Archive a note")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "Note archived"),
    	@ApiResponse(responseCode = "404", description = "Note not found")
    })
    @PutMapping("/{id}/archive")
    public ResponseEntity<NoteDTO> archiveNote(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.archive(id));
    }

    @Operation(summary = "Unarchive a note")
    @ApiResponses({
		@ApiResponse(responseCode = "200", description = "Note unarchived"),
		@ApiResponse(responseCode = "404", description = "Note not found")
	})
    @PutMapping("/{id}/unarchive")
    public ResponseEntity<NoteDTO> unarchiveNote(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.unarchive(id));
    }
    
    @Operation(summary = "Add a tag to a note")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "Tag added to note"),
    	@ApiResponse(responseCode = "404", description = "Note not found")
    })
    @PutMapping("/{noteId}/tags/{tagName}")
    public ResponseEntity<NoteDTO> addTag(
    		@PathVariable Long noteId,
    		@PathVariable String tagName) {
    	return ResponseEntity.ok(noteService.addTag(noteId, tagName));
    }
    
    @Operation(summary = "Remove a tag from a note")
    @ApiResponses({
		@ApiResponse(responseCode = "200", description = "Tag removed from note"),
		@ApiResponse(responseCode = "404", description = "Note not found")
	})
    @DeleteMapping("/{noteId}/tags/{tagName}")
    public ResponseEntity<NoteDTO>	removeTag(
    		@PathVariable Long noteId,
			@PathVariable String tagName) {
		return ResponseEntity.ok(noteService.removeTag(noteId, tagName));
	}	
    
    @Operation(summary = "Get notes by tag")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "List of notes with the specified tag")
    })
    @GetMapping("/by-tag/{tagName}")public List<NoteDTO> getByTag(@PathVariable String tagName) {
    	return noteService.getByTag(tagName);
    }
}
