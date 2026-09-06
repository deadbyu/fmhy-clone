package com.fmhyclone.entity;


import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;

    @ManyToMany(mappedBy = "tags")
    @Builder.Default
    private Set<Link> links = new HashSet<>();
}
    