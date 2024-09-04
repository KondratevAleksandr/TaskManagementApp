package com.example.TaskManagementApp.Service;

import com.example.TaskManagementApp.entity.Tag;
import com.example.TaskManagementApp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    public ResponseEntity<Tag> getTagById(Integer id) {
        return tagRepository.findById(id)
                .map(tag -> new ResponseEntity<>(tag, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Tag> saveTag(Tag tag) {
        Tag savedTag = tagRepository.save(tag);
        return new ResponseEntity<>(savedTag, HttpStatus.CREATED);
    }

    public ResponseEntity<Tag> updateTag(Integer id, Tag tagDetails) {
        Optional<Tag> tagOptional = tagRepository.findById(id);

        if (tagOptional.isPresent()) {
            Tag tag = tagOptional.get();
            tag.setId(tagDetails.getId());
            tag.setName(tagDetails.getName());
            tag.setTaskList(tagDetails.getTaskList());
            tagRepository.save(tag);
            return ResponseEntity.ok(tag);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deleteTag(Integer id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
