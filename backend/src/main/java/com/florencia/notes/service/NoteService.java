package com.florencia.notes.service;

import com.florencia.notes.model.Note;
import com.florencia.notes.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
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
}
