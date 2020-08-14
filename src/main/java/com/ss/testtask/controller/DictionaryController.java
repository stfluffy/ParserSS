package com.ss.testtask.controller;

import com.ss.testtask.dto.DictionaryDto;
import com.ss.testtask.service.DictionaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @GetMapping("/parser/{id}")
    public ResponseEntity<DictionaryDto> getDictionaryById(@PathVariable("id") long id) {
        Optional<DictionaryDto> dictionaryDto = dictionaryService.getById(id);

        return dictionaryDto.map(dictionary -> new ResponseEntity<>(dictionary, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/parser")
    public ResponseEntity<DictionaryDto> addDictionary(@RequestBody String url) throws Exception {
        Optional<DictionaryDto> dictionaryDto = dictionaryService.addDictionary(url);

        return dictionaryDto.map(dictionary -> new ResponseEntity<>(dictionary, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PutMapping("/parser/{id}")
    public ResponseEntity<DictionaryDto> updateDictionary(@PathVariable("id") long id) throws Exception {
        Optional<DictionaryDto> dictionaryDto = dictionaryService.updateDictionary(id);

        return dictionaryDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/parser/{id}")
    public ResponseEntity deleteDictionary(@PathVariable("id") long id) {
       return dictionaryService.deleteDictionary(id) ?
               new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
