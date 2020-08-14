package com.ss.testtask.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "dictionary")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @ElementCollection
    @Column(name = "count_words")
    @MapKeyColumn(name = "words")
    private Map<String, String> words = new HashMap<>();

    @Column(name = "fileName")
    private String fileName;

}
