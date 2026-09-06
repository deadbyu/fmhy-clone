package com.fmhyclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fmhyclone.entity.Tag;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findBySlug(String slug);
}
