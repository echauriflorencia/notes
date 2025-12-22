package com.florencia.notes.mapper;

import com.florencia.notes.dto.NoteDTO;
import com.florencia.notes.dto.TagDTO;
import com.florencia.notes.model.Note;

import java.util.List;
import java.util.stream.Collectors;

public class NoteMapper {

    public static NoteDTO toDTO(Note note) {
        NoteDTO dto = new NoteDTO();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        dto.setArchived(note.isArchived());

        List<TagDTO> tags = note.getTags().stream()
                .map(tag -> {
                    TagDTO t = new TagDTO();
                    t.setId(tag.getId());
                    t.setName(tag.getName());
                    return t;
                })
                .collect(Collectors.toList());

        dto.setTags(tags);
        return dto;
    }
    
    public static Note toEntity(NoteDTO dto) {
        Note note = new Note();
        note.setId(dto.getId());
		note.setTitle(dto.getTitle());
		note.setContent(dto.getContent());
		note.setArchived(dto.isArchived());

		return note;
    }
}
