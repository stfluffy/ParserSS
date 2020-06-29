package com.ss.testtask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Entity @Table(name = "dictionary")
public class Dictionary {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @ElementCollection
    @Column(name = "count_words")
    @MapKeyColumn(name = "words")
    private Map<String, String> words = new HashMap<>();

    @Column(name = "fileName")
    @JsonIgnore
    private String fileName;

    public Dictionary() {

    }

    public Dictionary(String url) {
        this.url = url;
    }

    public Dictionary(String url, Map<String, String> words) {
        this.url = url;
        this.words = words;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getWords() {
        return words;
    }

    public void setWords(Map<String, String> words) {
        this.words = words;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Dictionary { \n");
        sb.append("ID = ").append(id).append(", \n");
        sb.append("URL = ").append(url).append(", \n");

        sb.append("words = ").append(" [ \n");
        for (String key : words.keySet()) {
            sb.append(" ").append(key).append(" = ").append(words.get(key)).append(",\n");
        }
        sb.append(" ]\n");
        sb.append("FileName = ").append(fileName).append(", \n");
        sb.append("}");
        return sb.toString();
    }
}
