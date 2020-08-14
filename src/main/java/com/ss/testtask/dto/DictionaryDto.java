package com.ss.testtask.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryDto {

    private Long id;
    private String url;
    private HashMap<String, String> words;
    @JsonIgnore
    private String fileName;

}
