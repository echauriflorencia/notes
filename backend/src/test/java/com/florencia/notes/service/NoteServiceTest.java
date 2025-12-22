package com.florencia.notes.service;

import com.florencia.notes.dto.NoteDTO;
import com.florencia.notes.dto.TagDTO;
import com.florencia.notes.model.Note;
import com.florencia.notes.model.Tag;
import com.florencia.notes.repository.NoteRepository;
import com.florencia.notes.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class NoteServiceTest {
	@Mock
	private NoteRepository noteRepository;
	
	@Mock
	private TagRepository tagRepository;
	
	@InjectMocks
	private NoteService noteService;
	
	@BeforeEach 
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void shouldCreateNote() {
		Note note = new Note();
		note.setTitle("Test");
		note.setContent("Content");
		note.setArchived(false);
		
		when(noteRepository.save(any(Note.class))).thenReturn(note);
		
		NoteDTO result = noteService.create(
				new NoteDTO("Test", "Content", false)
		);
		
		assertThat(result.getTitle()).isEqualTo("Test");
		assertThat(result.isArchived()).isFalse();
		
		verify(noteRepository).save(any(Note.class));	
	}
	
	@Test 
	void shouldReturnAllNotes() {
		Note note1 = new Note();
		note1.setId(1L);
		note1.setTitle("Note 1");
		
		Note note2 = new Note();
		note2.setId(2L);
		note2.setTitle("Note 2");
		
		when(noteRepository.findAll()).thenReturn(List.of(note1, note2));
		
		List<NoteDTO> result = noteService.getAll();
		
		assertThat(result).hasSize(2);
		assertThat(result)
			.extracting(NoteDTO::getTitle)
			.containsExactly("Note 1", "Note 2");
	}
	
	@Test
	void shouldGetNoteById() {
		Note note = new Note();
		note.setId(1L);
		note.setTitle("Test");
		note.setContent("Content");
		
		when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
		
		NoteDTO result = noteService.getById(1L);
		
		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getTitle()).isEqualTo("Test");
	}
	
	@Test
	void shouldThrowWhenNoteNotFound() {
		when(noteRepository.findById(1L)).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> noteService.getById(1L))
			.isInstanceOf(RuntimeException.class)
			.hasMessage("Note not found");
	}
	
	
	@Test
	void shouldArchiveNote() {
		Note note = new Note();
		note.setId(1L);
		note.setArchived(false);
		
		when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
		when(noteRepository.save(any(Note.class))).thenReturn(note);
		
		NoteDTO result = noteService.archive(1L);
		
		assertThat(result.isArchived()).isTrue();
	}
	
	@Test
	void shouldUnarchiveNote() {
		Note note = new Note();
		note.setId(1L);
		note.setArchived(true);
		
		when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
		when(noteRepository.save(any(Note.class))).thenReturn(note);
		
		NoteDTO result = noteService.unarchive(1L);
		
		assertThat(result.isArchived()).isFalse();
	}
	
	@Test
	void shouldAddNewTagToNote() {
		Note note = new Note();
		note.setId(1L);
		note.setTags(new HashSet<>());
		
		when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
		when(tagRepository.findByName("work")).thenReturn(Optional.empty());
		when(tagRepository.save(any(Tag.class))).thenAnswer(i -> i.getArgument(0));
		when(noteRepository.save(any(Note.class))).thenReturn(note);
		
		NoteDTO result = noteService.addTag(1L, "work");
		
		assertThat(result.getTags())
			.extracting(TagDTO::getName)
			.contains("work");
	}
	
	@Test
	void shouldRemoveTagFromNote() {
	    Tag tag = new Tag();
	    tag.setName("work");

	    Note note = new Note();
	    note.setId(1L);
	    note.setTags(new HashSet<>(Set.of(tag)));

	    when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
	    when(tagRepository.findByName("work")).thenReturn(Optional.of(tag));
	    when(noteRepository.save(any(Note.class))).thenReturn(note);

	    NoteDTO result = noteService.removeTag(1L, "work");

	    assertThat(result.getTags())
	    	.extracting(TagDTO::getName)
	    	.doesNotContain("work");
	}

}