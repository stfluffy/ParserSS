package com.ss.testtask.service;


import com.ss.testtask.dto.DictionaryDto;

import java.util.Optional;

public interface DictionaryService {

    /**
     * This method will return a dictionary from the database by ID.
     * @param id dictionary
     * @return
     */
    Optional<DictionaryDto> getById(long id);

    /**
     * This method creates a new dictionary.
     * @param url of web cite
     * @return DictionaryDto after add
     * @throws Exception
     */
    Optional<DictionaryDto> addDictionary(String url) throws Exception;

    /**
     * This method removes the old dictionary from local storage and database and creates a new one with identical url.
     * @param id dictionary
     * @return DictionaryDto after update
     * @throws Exception
     */
    Optional<DictionaryDto> updateDictionary(long id) throws Exception;

    /**
     * This method removes the dictionary from local storage and database.
     *
     * @param id dictionary
     * @return boolean
     */
    boolean deleteDictionary(long id);
}
