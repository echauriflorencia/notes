package com.florencia.notes.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.florencia.notes.dto.NoteDTO;
import com.florencia.notes.dto.TagDTO;
import com.florencia.notes.exception.ResourceNotFoundException;
import com.florencia.notes.service.NoteService;


@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnNoteById() throws Exception {
        NoteDTO dto = new NoteDTO(
                1L,
                "Test",
                "Content",
                false,
                List.of(new TagDTO(1L, "work"))
        );

        when(noteService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/notes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test"))
                .andExpect(jsonPath("$.tags[0].name").value("work"));
    }
    
    @Test
    void shouldReturnAllNotes() throws Exception {
        when(noteService.getAll()).thenReturn(List.of(
                new NoteDTO(1L, "A", "B", false, List.of()),
                new NoteDTO(2L, "C", "D", false, List.of())
        ));

        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("A"));
    }

    @Test
    void shouldCreateNote() throws Exception {
        NoteDTO input = new NoteDTO(
                null,
                "New note",
                "Content",
                false,
                List.of()
        );

        NoteDTO saved = new NoteDTO(
                1L,
                "New note",
                "Content",
                false,
                List.of()
        );

        when(noteService.create(any())).thenReturn(saved);

        mockMvc.perform(post("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New note"));
    }

    @Test
    void shouldAddTagToNote() throws Exception {
        NoteDTO dto = new NoteDTO(
                1L,
                "Test",
                "Content",
                false,
                List.of(new TagDTO(1L, "work"))
        );

        when(noteService.addTag(1L, "work")).thenReturn(dto);

        mockMvc.perform(put("/api/notes/{id}/tags/{tag}", 1L, "work"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tags[0].name").value("work"));
    }
    
    @Test
    void shouldReturn404WhenNoteNotFound() throws Exception {
    	when(noteService.getById(99L))
        .thenThrow(new ResourceNotFoundException("Note not found with id 99"));

        mockMvc.perform(get("/api/notes/{id}", 99L))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("Note not found with id 99"));
    }

}
