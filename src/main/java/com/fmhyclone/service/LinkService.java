package com.fmhyclone.service;

import com.fmhyclone.dto.CategoryResponse;
import com.fmhyclone.dto.LinkRequest;
import com.fmhyclone.dto.LinkResponse;
import com.fmhyclone.dto.TagResponse;
import com.fmhyclone.entity.Link;
import com.fmhyclone.entity.LinkStatus;
import com.fmhyclone.entity.Tag;
import com.fmhyclone.repository.LinkRepository;
import com.fmhyclone.repository.CategoryRepository;
import com.fmhyclone.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public LinkService(LinkRepository linkRepository,
                       TagRepository tagRepository,
                       CategoryRepository categoryRepository) {
        this.linkRepository = linkRepository;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<LinkResponse> getLinksByCategory(String slug, Pageable pageable) {
        return linkRepository.findLinksByCategorySlug(slug, pageable)
                .map(this::mapToResponse);
    }

    public Page<LinkResponse> getLinksByTag(String slug, Pageable pageable) {
        return linkRepository.findByTagSlug(slug, pageable)
                .map(this::mapToResponse);
    }

    public Page<LinkResponse> search(String query, Pageable pageable) {
        return linkRepository.search(query, pageable)
                .map(this::mapToResponse);
    }

    public LinkResponse createLink(LinkRequest request) {
        var category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found" + request.categoryId()));

        Set<Tag> tags = resolveTags(request.tagIds());

        Link link = Link.builder()
                .title(request.title())
                .url(request.url())
                .description(request.description())
                .status(LinkStatus.valueOf(request.status()))
                .category(category)
                .tags(tags)
                .build();

        return mapToResponse(linkRepository.save(link));
    }

    public LinkResponse updateLink(Long id, LinkRequest request) {
        var link = linkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Link not found" + id));
        var category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found" + request.categoryId()));
        
        link.setTitle(request.title());
        link.setUrl(request.url());
        link.setDescription(request.description());
        link.setStatus(LinkStatus.valueOf(request.status()));
        link.setCategory(category);
        link.setTags(resolveTags(request.tagIds()));

        return mapToResponse(linkRepository.save(link)); 
    }

    public void deleteLink(Long id){
        var link = linkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Link not found" + id));
        linkRepository.delete(link);
    }

    private Set<Tag> resolveTags(Set<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) return Set.of();
        return tagIds.stream()
                .map(tagId -> tagRepository.findById(tagId)
                        .orElseThrow(() -> new RuntimeException("Tag not found: " + tagId)))
                .collect(Collectors.toSet());
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
                .map(tag -> new TagResponse(tag.getId(), tag.getName(), tag.getSlug()))
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