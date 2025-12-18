package com.florencia.notes.controller;

import com.florencia.notes.model.Note;
import com.florencia.notes.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*") // para React, permitimos cualquier origen
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return noteService.create(note);
    }

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAll();
    }

    @GetMapping("/active")
    public List<Note> getActiveNotes() {
        return noteService.getActive();
    }

    @GetMapping("/archived")
    public List<Note> getArchivedNotes() {
        return noteService.getArchived();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        return noteService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note noteDetails) {
        return noteService.getById(id)
                .map(note -> {
                    note.setTitle(noteDetails.getTitle());
                    note.setContent(noteDetails.getContent());
                    Note updated = noteService.update(note);
                    return ResponseEntity.ok(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity<Note> archiveNote(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.archive(id));
    }

    @PutMapping("/{id}/unarchive")
    public ResponseEntity<Note> unarchiveNote(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.unarchive(id));
    }
}
