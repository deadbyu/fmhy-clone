// com/fmhyclone/service/TagService.java
package com.fmhyclone.service;

import com.fmhyclone.dto.TagResponse;
import com.fmhyclone.entity.Tag;
import com.fmhyclone.repository.TagRepository;
import org.springframework.stereotype.Service;
import com.fmhyclone.dto.TagRequest;
import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagResponse> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(tag -> new TagResponse(tag.getId(), tag.getName(), tag.getSlug()))
                .toList();
    }

    public TagResponse createTag(TagRequest request) {
        Tag tag = Tag.builder()
                .name(request.name())
                .slug(request.slug())
                .build();

        return mapToResponse(tagRepository.save(tag));
    }

    public void deleteTag(Long id){
        if(!tagRepository.existsById(id)){
            throw new RuntimeException("Tag with id " + id + " does not exist");

        }
        tagRepository.deleteById(id);
    }

    public TagResponse mapToResponse(Tag tag) {
        return new TagResponse(tag.getId(), tag.getName(), tag.getSlug());
    }
}