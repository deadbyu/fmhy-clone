package com.fmhyclone.service;

import com.fmhyclone.dto.CategoryResponse;
import com.fmhyclone.dto.LinkResponse;
import com.fmhyclone.dto.TagResponse;
import com.fmhyclone.entity.Link;
import com.fmhyclone.repository.LinkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LinkService {

    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository){
        this.linkRepository = linkRepository;
    }

    public Page<LinkResponse> getLinksByCategory(String categorySlug, Pageable pageable) {
        return linkRepository.findLinksByCategorySlug(categorySlug, pageable)
                .map(this::mapToResponse);
    }

    public Page<LinkResponse> search(String query, Pageable pageable) {
        return linkRepository.search(query, pageable)
                .map(this::mapToResponse);
    }

    private LinkResponse mapToResponse(Link link) {
        CategoryResponse categoryResponse = new CategoryResponse(
            link.getCategory().getId(),
            link.getCategory().getName(),
            link.getCategory().getDescription(),
            link.getCategory().getIcon(),
            link.getCategory().getSlug(),
            link.getCategory().getDisplayOrder()
        );

        Set<TagResponse> tagResponses = link.getTags().stream()
            .map(tag -> new TagResponse(
                tag.getId(),
                tag.getName(),
                tag.getSlug()
            ))
            .collect(Collectors.toSet());


        return new LinkResponse(
            link.getId(),
            link.getTitle(),
            link.getUrl(),
            link.getDescription(),
            link.getStatus() != null ? link.getStatus().name() : null,
            categoryResponse,
            tagResponses
        );
    }
}