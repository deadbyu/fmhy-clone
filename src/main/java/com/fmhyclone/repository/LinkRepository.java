package com.fmhyclone.repository;

import com.fmhyclone.entity.Link;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Page<Link> findLinksByCategorySlug(String slug, Pageable pageable);


    @Query("SELECT l FROM Link l JOIN l.tags t WHERE t.slug = :slug")
    Page<Link> findByTagSlug(@Param("slug") String slug, Pageable pageable);

    @Query("""
        SELECT DISTINCT l FROM Link l
        LEFT JOIN l.tags t
        WHERE LOWER(l.title) LIKE LOWER(CONCAT('%', :q, '%'))
            OR LOWER(l.description) LIKE LOWER(CONCAT('%', :q, '%'))
            OR LOWER(t.name) LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    Page<Link> search(@Param("q") String query, Pageable pageable);
}
