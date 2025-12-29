package com.florencia.notes.integration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.florencia.notes.dto.NoteDTO;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

class NoteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAndRetrieveNote() throws Exception {
        NoteDTO input = new NoteDTO(
                null,
                "Integration note",
                "Saved in DB",
                false,
                null
        );

        mockMvc.perform(post("/api/notes")
        		.with(httpBasic("test", "test123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Integration note"));

        mockMvc.perform(get("/api/notes")
        		.with(httpBasic("test", "test123")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].title")
                .value(org.hamcrest.Matchers.hasItem("Integration note")));

    }
    
    @Test
    void shouldAddTagToExistingNote() throws Exception {
        NoteDTO input = new NoteDTO(
                null,
                "Note with tag",
                "Content",
                false,
                null
        );

        String response = mockMvc.perform(post("/api/notes")
        		.with(httpBasic("test", "test123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        NoteDTO created = objectMapper.readValue(response, NoteDTO.class);

        mockMvc.perform(
                put("/api/notes/{id}/tags/{tag}", created.getId(), "work")
                .with(httpBasic("test", "test123"))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.tags[0].name").value("work"));
    }

}
