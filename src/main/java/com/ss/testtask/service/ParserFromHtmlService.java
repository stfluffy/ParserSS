package com.ss.testtask.service;

import com.ss.testtask.controller.DictionaryController;
import com.ss.testtask.model.Dictionary;
import com.ss.testtask.repository.DictionaryRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

@Service
public class ParserFromHtmlService {
    private static final Logger logger = Logger.getLogger(DictionaryController.class);

    @Autowired
    DictionaryRepository dictionaryRepository;

    /**
     * Location of the folder with saved web pages.
     */
    private final String pagesFolder = "saveWebPages/";

    /**
     * This method checks ServiceResult<> for errors received while parsing a web page,
     * as well as if a Dictionary with such URL already exists.
     *
     * @param url of cite.
     * @return ServiceResult<> with exception or ServiceResult<> with a Dictionary instance.
     */
    public ServiceResult<Dictionary> parsePage(String url) {
        ServiceResult<Dictionary> result;
        url = getUrl(url);

        if (dictionaryRepository.findByUrl(url) != null) {
            return new ServiceResult<>(dictionaryRepository.findByUrl(url));
        }

        result = savePageToFile(url, getFileName(url));
        if(result.hasError()) {
            return new ServiceResult<>("ParserFromHtmlService." + result.getError());
        }

        result = getDictionary(url, getFileName(url));
        if(result.hasError()) {
            return new ServiceResult<>("ParserFromHtmlService." + result.getError());
        }

        return result;
    }

    /**
     * This method parses a web page stored in a folder,
     *      searches for unique words and counts their number.
     * Then creates a new object Dictionary and returns it.
     *
     * @param url of cite after the method getUrl().
     * @param fileName after the method getFileName().
     *
     *
     * @return ServiceResult<> with exception or ServiceResult<> with a Dictionary instance.
     */
    private ServiceResult<Dictionary> getDictionary(String url, String fileName) {
        HashMap<String, String> words = new HashMap<>();
        Dictionary dictionary = new Dictionary(getUrl(url));

        try {
            String getParseText =
                    Jsoup.parse(new File(pagesFolder + fileName + ".html"), "UTF-8")
                    .text();

            String[] parseText = Jsoup.parse(getParseText).text()
                    .replaceAll("[^A-Za-zа-яёА-ЯЁ ]", " ")
                    .replaceAll("\\s+", " ")
                    .toLowerCase()
                    .split(" ");

            for (String key : parseText) {
                int count = 0;
                for (String value : parseText) {
                    if (key.equals(value)) {
                        count++;
                    }
                }

                words.put(key, String.valueOf(count));
            }

            dictionary.setFileName(pagesFolder + fileName + ".html");
            dictionary.setWords(words);
            return new ServiceResult<>(dictionaryRepository.save(dictionary));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return new ServiceResult<>("getDictionary: " + e.getMessage());
        }
    }

    /**
     * This method downloads a web page,
     *      creates a new file with the name of the site and saves it.
     *
     * @param url of cite after the method getUrl().
     * @param fileName after the method getFileName().
     *
     * @return ServiceResult<> with exception or empty ServiceResult<>.
     */
    private ServiceResult<Dictionary> savePageToFile(String url, String fileName) {
        try {
            String getText = Jsoup.connect(url)
                    .timeout(3000)
                    .get().toString();

            BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(pagesFolder + fileName + ".html"));
            bufferedWriter.write(getText);

            return new ServiceResult<>();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return new ServiceResult<>("savePageToFile: " + e.getMessage());
        }
    }

    /**
     * @param url of site.
     * @return a string without a slash at the end, for the parser to work correctly.
     */
    public String getUrl(String url) {
        return StringUtils.stripEnd(url, "/");
    }

    /**
     * @param url of site.
     * @return a string without '/', and a protocol for writing the file name.
     */
    private String getFileName(String url) {
        return StringUtils.substringAfter(url, "://").replace("/", "-");
    }
}
