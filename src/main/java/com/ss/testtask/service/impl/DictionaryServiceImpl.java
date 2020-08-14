package com.ss.testtask.service.impl;

import com.ss.testtask.dto.DictionaryDto;
import com.ss.testtask.model.Dictionary;
import com.ss.testtask.repository.DictionaryRepository;
import com.ss.testtask.service.DictionaryService;
import com.ss.testtask.service.ParserFromHtmlService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    private final ParserFromHtmlService parser;

    private final ModelMapper modelMapper;

    @Override
    public Optional<DictionaryDto> getById(long id) {
        Optional<Dictionary> dictionary = dictionaryRepository.findById(id);
        return dictionary.flatMap(this::convertToDto);
    }

    @Override
    public Optional<DictionaryDto> addDictionary(String url) {
        try {
            JSONObject jsonObject = new JSONObject(url);
            return Optional.of(parser.parsePage(jsonObject.getString("url")));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<DictionaryDto> updateDictionary(long id) throws Exception {
        Optional<Dictionary> dictionaryById = dictionaryRepository.findById(id);

        if (dictionaryById.isPresent() && deleteFile(dictionaryById.get().getFileName()) ) {
            dictionaryRepository.deleteById(id);
            return Optional.of(parser.parsePage(dictionaryById.get().getUrl()));
        }

        return Optional.empty();
    }

    @Override
    public boolean deleteDictionary(long id) {
        Optional<Dictionary> dictionaryById = dictionaryRepository.findById(id);

        if (dictionaryById.isPresent()) {
            dictionaryRepository.deleteById(id);
            return deleteFile(dictionaryById.get().getFileName()) ;
        }
        return false;
    }

    private boolean deleteFile(String fileName) {
        try {
            File fileToDelete = new File(fileName);
            return fileToDelete.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Optional<DictionaryDto> convertToDto(Dictionary dictionary) {
        return Optional.of(modelMapper.map(dictionary, DictionaryDto.class));
    }
}
