package com.ss.testtask.service;


import com.ss.testtask.dto.DictionaryDto;

public interface ParserFromHtmlService {

    /**
     * This method returns a DictionaryDto.class after parsing the web page and
     * saving the entity to the database and local storage.
     *
     * @param url of cite.
     * @return DictionaryDto.
     */
    DictionaryDto parsePage(String url) throws Exception;
}
