package com.florencia.notes.repository;

import com.florencia.notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByArchivedFalse();
    List<Note> findByArchivedTrue();
    
    @Query("select n from Note n join n.tags t where t.name = :tagName")
    List<Note> findByTagName(String tagName);
    
}
