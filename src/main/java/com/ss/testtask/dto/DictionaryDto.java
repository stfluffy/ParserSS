package com.ss.testtask.dto;

import java.util.HashMap;

public class DictionaryDto {
    private String url;
    private HashMap<String, String> words;

    public DictionaryDto() {

    }

    public DictionaryDto(String url, HashMap<String, String> words) {
        this.url = url;
        this.words = words;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, String> getWords() {
        return words;
    }

    public void setWords(HashMap<String, String> words) {
        this.words = words;
    }
}
