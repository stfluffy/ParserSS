package com.ss.testtask.controller;

import com.ss.testtask.model.Dictionary;
import com.ss.testtask.repository.DictionaryRepository;
import com.ss.testtask.service.ParserFromHtmlService;
import com.ss.testtask.service.ServiceResult;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DictionaryController {
    private static final Logger logger = Logger.getLogger(DictionaryController.class);

    @Autowired
    ParserFromHtmlService parser;

    @Autowired
    DictionaryRepository dictionaryRepository;

    @GetMapping("/parser/{id}")
    public ResponseEntity<ServiceResult<Dictionary>> getDictionaryById( @PathVariable("id") long id) {
        Optional<Dictionary> dictionaryById = dictionaryRepository.findById(id);

        return dictionaryById.map(dictionary -> new ResponseEntity<>(new ServiceResult<>(dictionary),
                HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new ServiceResult<>("notFoundById: " + id),
                        HttpStatus.NOT_FOUND));
    }

    @PostMapping("/parser")
    public ResponseEntity<ServiceResult<Dictionary>> createDictionary( @RequestBody String url) {
        ServiceResult<Dictionary> serviceResult;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(url);
            url = jsonObject.getString("url");
            Jsoup.connect(url);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>( new ServiceResult<>("addDictionary: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }

        serviceResult = parser.parsePage(url);

        if (serviceResult.hasError()) {
            return new ResponseEntity<>(new ServiceResult<>(serviceResult.getError()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>( new ServiceResult<>(serviceResult.getResult()),
                HttpStatus.OK);
    }

    @PutMapping("/parser/{id}")
    public ResponseEntity<ServiceResult<Dictionary>> updateDictionary( @PathVariable("id") long id,
                                                                       @RequestBody Dictionary dictionary) {
        Optional<Dictionary> dictionaryById = dictionaryRepository.findById(id);

        if (dictionaryById.isPresent()) {
            Dictionary dictionaryToSave = dictionaryById.get();

            if (!dictionary.getUrl().equals(dictionaryToSave.getUrl())) {
                dictionaryToSave.setUrl(parser.getUrl(dictionary.getUrl()));
            }
            if (!dictionary.getWords().isEmpty()) {
                dictionaryToSave.setWords(dictionary.getWords());
            }

            return new ResponseEntity<>( new ServiceResult<>(dictionaryRepository.save(dictionaryToSave)),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(new ServiceResult<>("notFoundId:" + id),
                HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/parser/{id}")
    public ResponseEntity<ServiceResult<Dictionary>> deleteDictionary(@PathVariable("id") long id) {
        Optional<Dictionary> getDictionary = dictionaryRepository.findById(id);
        boolean isDelete = false;

        try {
           if (getDictionary.isPresent()) {
               File fileToDelete = new File(getDictionary.get().getFileName());
               isDelete = fileToDelete.delete();
           }

           if(!isDelete) {
               return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
           }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            dictionaryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>( new ServiceResult<>("notFoundId:" + id), HttpStatus.NOT_FOUND);
        }
    }
}
